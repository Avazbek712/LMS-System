package uz.imv.lmssystem.serviceImpl.lessons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.AttendanceStatus;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.exceptions.EntityUniqueException;
import uz.imv.lmssystem.mapper.AttendanceMapper;
import uz.imv.lmssystem.mapper.resolvers.LessonResolver;
import uz.imv.lmssystem.mapper.resolvers.StudentResolver;
import uz.imv.lmssystem.repository.AttendanceRepository;
import uz.imv.lmssystem.repository.LessonRepository;
import uz.imv.lmssystem.repository.StudentRepository;
import uz.imv.lmssystem.utils.AttendanceValidate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

    // --- Создаем "фальшивые" версии всех зависимостей ---
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private AttendanceMapper attendanceMapper;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private StudentResolver studentResolver;
    @Mock
    private LessonResolver lessonResolver;
    @Mock
    private AttendanceValidate attendanceValidate;
    // AuthService не нужен для метода save, поэтому его можно не мокать

    // --- Указываем, какой класс мы тестируем, и просим Mockito внедрить в него все @Mock'и ---
    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    // --- Подготовим тестовые данные, которые будем использовать в разных тестах ---
    private AttendanceDTO inputDTO;
    private User currentUser;
    private Attendance mockAttendanceEntity;
    private AttendanceDTO expectedOutputDTO;

    @BeforeEach
    void setUp() {
        // Этот метод будет выполняться перед каждым тестом, создавая чистые данные
        currentUser = new User();
        currentUser.setId(1L);

        inputDTO = new AttendanceDTO(AttendanceStatus.PRESENT, 10L, 20L);

        mockAttendanceEntity = new Attendance();
        mockAttendanceEntity.setId(100L);
        // ... можно добавить другие поля, если нужно

        expectedOutputDTO = new AttendanceDTO(AttendanceStatus.PRESENT, 10L, 20L);
    }

    @Test
    @DisplayName("Успешное сохранение, когда все данные корректны (Happy Path)")
    void save_shouldReturnSavedAttendanceDTO_whenDataIsValid() {
        // --- 1. Arrange (Подготовка) ---
        // Настраиваем поведение наших моков, чтобы все проверки прошли успешно

        // Проверка существования студента и урока
        when(studentRepository.existsById(10L)).thenReturn(true);
        when(lessonRepository.existsById(20L)).thenReturn(true);
        // Проверка на уникальность
        when(attendanceRepository.existsByStudentIdAndLessonId(10L, 20L)).thenReturn(false);

        // Настраиваем маппер: когда его попросят превратить DTO в Entity, он вернет наш мок
        when(attendanceMapper.toEntity(inputDTO, studentResolver, lessonResolver)).thenReturn(mockAttendanceEntity);

        // Настраиваем валидатор: говорим, что его методы должны просто выполниться и ничего не делать
        doNothing().when(attendanceValidate).checkTeacherAccessToAttendance(any(Attendance.class), any(User.class));
        doNothing().when(attendanceValidate).validateLessonTiming(any(Attendance.class), eq(false));

        // Настраиваем репозиторий: когда его попросят сохранить сущность, он вернет ее же
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(mockAttendanceEntity);

        // Настраиваем маппер: когда его попросят превратить сохраненную Entity в DTO, он вернет наш ожидаемый результат
        when(attendanceMapper.toDTO(mockAttendanceEntity)).thenReturn(expectedOutputDTO);

        // --- 2. Act (Действие) ---
        // Вызываем тестируемый метод
        AttendanceDTO resultDTO = attendanceService.save(inputDTO, currentUser);

        // --- 3. Assert (Проверка) ---
        // Проверяем, что результат не null и соответствует тому, что мы ожидали
        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getStudentId()).isEqualTo(inputDTO.getStudentId());
        assertThat(resultDTO.getLessonId()).isEqualTo(inputDTO.getLessonId());
        assertThat(resultDTO.getStatus()).isEqualTo(inputDTO.getStatus());

        // Дополнительно проверяем, что все важные методы были вызваны ровно 1 раз
        verify(studentRepository, times(1)).existsById(10L);
        verify(lessonRepository, times(1)).existsById(20L);
        verify(attendanceRepository, times(1)).existsByStudentIdAndLessonId(10L, 20L);
        verify(attendanceValidate, times(1)).checkTeacherAccessToAttendance(mockAttendanceEntity, currentUser);
        verify(attendanceRepository, times(1)).save(mockAttendanceEntity);
        verify(attendanceMapper, times(1)).toDTO(mockAttendanceEntity);
    }

    @Test
    @DisplayName("Выбросить исключение, если студент не найден")
    void save_shouldThrowEntityNotFoundException_whenStudentDoesNotExist() {
        // --- Arrange ---
        // Настраиваем мок так, чтобы он провалил проверку на существование студента
        when(studentRepository.existsById(10L)).thenReturn(false);

        // --- Act & Assert ---
        // Проверяем, что вызов метода приведет к выбросу именно этого исключения
        assertThrows(EntityNotFoundException.class, () -> {
            attendanceService.save(inputDTO, currentUser);
        });

        // Проверяем, что метод сохранения НИКОГДА не был вызван, так как логика прервалась раньше
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Выбросить исключение, если урок не найден")
    void save_shouldThrowEntityNotFoundException_whenLessonDoesNotExist() {
        // --- Arrange ---
        when(studentRepository.existsById(10L)).thenReturn(true);
        when(lessonRepository.existsById(20L)).thenReturn(false); // Проваливаем проверку урока

        // --- Act & Assert ---
        assertThrows(EntityNotFoundException.class, () -> {
            attendanceService.save(inputDTO, currentUser);
        });

        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Выбросить исключение, если посещаемость уже существует")
    void save_shouldThrowEntityUniqueException_whenAttendanceAlreadyExists() {
        // --- Arrange ---
        when(studentRepository.existsById(10L)).thenReturn(true);
        when(lessonRepository.existsById(20L)).thenReturn(true);
        when(attendanceRepository.existsByStudentIdAndLessonId(10L, 20L)).thenReturn(true); // Проваливаем проверку на уникальность

        // --- Act & Assert ---
        assertThrows(EntityUniqueException.class, () -> {
            attendanceService.save(inputDTO, currentUser);
        });

        verify(attendanceRepository, never()).save(any(Attendance.class));
    }
}