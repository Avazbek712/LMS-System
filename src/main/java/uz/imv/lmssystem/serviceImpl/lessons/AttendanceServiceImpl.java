package uz.imv.lmssystem.serviceImpl.lessons;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.dto.AttendanceStatusUpdateDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.PermissionsEnum;
import uz.imv.lmssystem.exceptions.AccessDeniedException;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.exceptions.EntityUniqueException;
import uz.imv.lmssystem.mapper.AttendanceMapper;
import uz.imv.lmssystem.mapper.resolvers.LessonResolver;
import uz.imv.lmssystem.mapper.resolvers.StudentResolver;
import uz.imv.lmssystem.repository.AttendanceRepository;
import uz.imv.lmssystem.repository.LessonRepository;
import uz.imv.lmssystem.repository.StudentRepository;
import uz.imv.lmssystem.service.AttendanceService;
import uz.imv.lmssystem.service.security.AuthService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {


    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final StudentResolver studentResolver;
    private final LessonResolver lessonResolver;
    private final AuthService authService;


    @Override
    public AttendanceDTO getById(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));
        checkTeacherAccessToAttendance(attendance);
        return attendanceMapper.toDTO(attendance);

    }

    @Override
    public PageableDTO getAll(Integer page, Integer size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = (User) authService.loadUserByUsername(username);
        Page<Attendance> attendancePage;
        if (authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_ALL, currentUser)) {
            attendancePage = attendanceRepository.findAll(pageable);
        } else if (authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_OWN, currentUser)) {
            attendancePage = attendanceRepository.findAllByLesson_Group_Teacher_Id(currentUser.getId(), pageable);
        } else {
            throw new AccessDeniedException("You do not have permission to view attendances.");
        }

        List<AttendanceDTO> attendanceDTOs = attendanceMapper.toDTO(attendancePage.getContent());
        if (attendanceDTOs.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, null);
        }
        return new PageableDTO(
                attendancePage.getSize(),
                attendancePage.getTotalElements(),
                attendancePage.getTotalPages(),
                !attendancePage.isLast(),
                !attendancePage.isFirst(),
                attendanceDTOs
        );
    }

    @Override
    @Transactional
    public AttendanceDTO save(AttendanceDTO dto) {
        if (!studentRepository.existsById(dto.getStudentId())) {
            throw new EntityNotFoundException("Student with id : " + dto.getStudentId() + " not found!");
        }
        if (!lessonRepository.existsById(dto.getLessonId())) {
            throw new EntityNotFoundException("Lesson with id : " + dto.getLessonId()
                                              + " not found!");
        }
        if (attendanceRepository.existsByStudentIdAndLessonId(dto.getStudentId(), dto.getLessonId())) {
            throw new EntityUniqueException("Attendance for student with id : " + dto.getStudentId()
                                            + " and lesson with id : " + dto.getLessonId() + " already exists!");
        }
        Attendance attendance = attendanceMapper.toEntity(dto, studentResolver, lessonResolver);
        checkTeacherAccessToAttendance(attendance);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(savedAttendance);
    }

    @Override
    @Transactional
    public AttendanceDTO update(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));

        if (!studentRepository.existsById(dto.getStudentId())) {
            throw new EntityNotFoundException("Student with id : " + dto.getStudentId() + " not found!");
        }
        if (!lessonRepository.existsById(dto.getLessonId())) {
            throw new EntityNotFoundException("Lesson with id : " + dto.getLessonId() + " not found!");
        }
        if (attendanceRepository.existsByStudentIdAndLessonId(dto.getStudentId(), dto.getLessonId()) && !attendance.getStudent().getId().equals(dto.getStudentId())) {
            throw new EntityUniqueException("Attendance for student with id : " + dto.getStudentId()
                                            + " and lesson with id : " + dto.getLessonId() + " already exists!");
        }

        attendanceMapper.updateEntity(dto, attendance, studentResolver, lessonResolver);
        checkTeacherAccessToAttendance(attendance);
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(updatedAttendance);

    }

    @Override
    @Transactional
    public AttendanceDTO updateStatus(Long id, AttendanceStatusUpdateDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));

        attendance.setStatus(dto.getStatus());
        checkTeacherAccessToAttendance(attendance);
        attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(attendance);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isEmpty()) {
            throw new EntityNotFoundException("Attendance with id : " + id + " not found!");
        }
        checkTeacherAccessToAttendance(attendance.get());
        attendanceRepository.deleteById(id);
    }

    private void checkTeacherAccessToAttendance(Attendance attendance) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = (User) authService.loadUserByUsername(username);
        if (authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_ALL, currentUser)) {
            return;
        } if (authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_OWN, currentUser)) {
            if (!attendance.getLesson().getGroup().getTeacher().getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("You are not allowed to access this attendance with id: " + attendance.getId());
            }
        } else {
            throw new AccessDeniedException("You are not allowed to access this attendance with id: " + attendance.getId());
        }
    }
}
