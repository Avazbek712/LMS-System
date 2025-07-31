package uz.imv.lmssystem.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.entity.Lesson;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.PermissionsEnum;
import uz.imv.lmssystem.exceptions.AccessDeniedException;
import uz.imv.lmssystem.exceptions.InvalidAttendanceTimeException;
import uz.imv.lmssystem.service.security.AuthService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class AttendanceValidate {

    private final AuthService authService;

    public void checkTeacherAccessToAttendance(Attendance attendance, User currentUser) {
        if (hasReadAllPermission(currentUser)) return;

        if (hasReadOwnPermission(currentUser)) {
            if (!isTeacherOfAttendanceGroup(attendance, currentUser)) {
                throw new AccessDeniedException("You are not the teacher of group id: " +
                                                attendance.getLesson().getGroup().getId());
            }

            if (!isStudentInSameGroup(attendance)) {
                throw new AccessDeniedException("Student with id " + attendance.getStudent().getId() +
                                                " does not belong to the same group as the lesson (group id: " +
                                                attendance.getLesson().getGroup().getId() + ")");
            }

            return;
        }

        throw new AccessDeniedException("You are not allowed to access this attendance.");
    }

    public void validateLessonTiming(Attendance attendance, boolean isUpdate) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Lesson lesson = attendance.getLesson();
        LocalDate lessonDate = lesson.getDate();

        if (!lessonDate.equals(today)) {
            throw new InvalidAttendanceTimeException("Attendance is only allowed on the day of the lesson.");
        }

        LocalTime allowedStart = lesson.getStartTime().minusMinutes(15);
        LocalTime allowedEnd = lesson.getEndTime().plusMinutes(15);

        if (now.isBefore(allowedStart) || now.isAfter(allowedEnd)) {
            throw new InvalidAttendanceTimeException("Attendance must be done during or shortly around the lesson time.");
        }

        if (isUpdate && lessonDate.isBefore(today)) {
            throw new InvalidAttendanceTimeException("Attendance for past lessons cannot be updated.");
        }
    }

    public void checkDeleteAccess(Attendance attendance, User currentUser) {
        if (hasReadAllPermission(currentUser)) return;

        if (hasReadOwnPermission(currentUser) && isTeacherOfAttendanceGroup(attendance, currentUser)) {
            if (attendance.getLesson().getDate().equals(LocalDate.now())) return;
            throw new AccessDeniedException("Teachers can only delete attendance on the lesson day.");
        }

        throw new AccessDeniedException("You are not allowed to delete this attendance.");
    }


    private boolean isTeacherOfAttendanceGroup(Attendance attendance, User user) {
        return attendance.getLesson().getGroup().getTeacher().getId().equals(user.getId());
    }

    private boolean isStudentInSameGroup(Attendance attendance) {
        return attendance.getLesson().getGroup().getId()
                .equals(attendance.getStudent().getGroup().getId());
    }

    private boolean hasReadAllPermission(User user) {
        return authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_ALL, user);
    }

    private boolean hasReadOwnPermission(User user) {
        return authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_OWN, user);
    }
}
