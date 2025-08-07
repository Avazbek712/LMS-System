package uz.imv.lmssystem.serviceImpl.lessons;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.dto.AttendanceStatusUpdateDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.PermissionsEnum;
import uz.imv.lmssystem.exceptions.AccessDeniedException;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.exceptions.EntityUniqueException;
import uz.imv.lmssystem.exceptions.PaymentStatusException;
import uz.imv.lmssystem.mapper.AttendanceMapper;
import uz.imv.lmssystem.mapper.resolvers.LessonResolver;
import uz.imv.lmssystem.mapper.resolvers.StudentResolver;
import uz.imv.lmssystem.repository.AttendanceRepository;
import uz.imv.lmssystem.repository.LessonRepository;
import uz.imv.lmssystem.repository.StudentRepository;
import uz.imv.lmssystem.service.lessons.AttendanceService;
import uz.imv.lmssystem.service.security.AuthService;
import uz.imv.lmssystem.utils.AttendanceValidate;

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
    private final AttendanceValidate attendanceValidate;


    @Override
    public AttendanceDTO getById(Long id, User currentUser) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));
        attendanceValidate.checkTeacherAccessToAttendance(attendance,currentUser);
        return attendanceMapper.toDTO(attendance);

    }

    @Override
    public PageableDTO getAll(Integer page, Integer size, User currentUser) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
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
    public AttendanceDTO save(AttendanceDTO dto, User currentUser) {
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
        attendanceValidate.checkTeacherAccessToAttendance(attendance, currentUser);
        attendanceValidate.validateLessonTiming(attendance, false);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(savedAttendance);
    }

    @Override
    @Transactional
    public AttendanceDTO update(Long id, AttendanceDTO dto, User currentUser) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));

        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new EntityNotFoundException("Student with id : " + dto.getStudentId() + " not found!"));

        if(!student.getPaymentStatus()) throw new PaymentStatusException("The student did not pay!");

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



        attendanceValidate.checkTeacherAccessToAttendance(attendance, currentUser);
        attendanceMapper.updateEntity(dto, attendance, studentResolver, lessonResolver);
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        attendanceValidate.validateLessonTiming(updatedAttendance, true);
        return attendanceMapper.toDTO(updatedAttendance);

    }

    @Override
    @Transactional
    public AttendanceDTO updateStatus(Long id, AttendanceStatusUpdateDTO dto,User currentUser) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance with id : " + id + " not found!"));

        attendance.setStatus(dto.getStatus());
        attendanceValidate.checkTeacherAccessToAttendance(attendance, currentUser);
        attendanceValidate.validateLessonTiming(attendance, true);
        attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(attendance);
    }


    @Override
    @Transactional
    public void deleteById(Long id, User currentUser) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isEmpty()) {
            throw new EntityNotFoundException("Attendance with id : " + id + " not found!");
        }
        attendanceValidate.checkTeacherAccessToAttendance(attendance.get(), currentUser);
        attendanceValidate.checkDeleteAccess(attendance.get(), currentUser);
        attendanceRepository.deleteById(id);
    }


}
