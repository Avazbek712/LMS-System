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

    public void checkTeacherAccessToAttendance(Attendance attendance,User currentUser) {

        boolean canReadAll = authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_ALL, currentUser);
        boolean canReadOwn = authService.hasPermission(PermissionsEnum.ATTENDANCE_READ_OWN, currentUser);

        if (canReadAll) return;

        if (canReadOwn) {
            Long teacherId = attendance.getLesson().getGroup().getTeacher().getId();
            Long currentUserId = currentUser.getId();
            Long lessonGroupId = attendance.getLesson().getGroup().getId();
            Long studentGroupId = attendance.getStudent().getGroup().getId();

            if (!teacherId.equals(currentUserId)) {
                throw new AccessDeniedException("You are not the teacher of group id: " + lessonGroupId);
            }

            if (!lessonGroupId.equals(studentGroupId)) {
                throw new AccessDeniedException("Student with id " + attendance.getStudent().getId() +
                                                " does not belong to the same group as the lesson (group id: " + lessonGroupId + ")");
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


    private void checkDeleteAccess(Attendance attendance,User currentUser) {

        boolean isAdmin = authService.hasPermission(PermissionsEnum.ATTENDANCE_DELETE_ALL, currentUser);
        boolean isTeacher = authService.hasPermission(PermissionsEnum.ATTENDANCE_DELETE_OWN, currentUser) &&
                            isTeacherOfAttendanceGroup(attendance, currentUser);

        if (isAdmin) return;

        if (isTeacher) {
            LocalDate today = LocalDate.now();
            LocalDate lessonDate = attendance.getLesson().getStartTime().toLocalDate();

            if (lessonDate.equals(today)) return;

            throw new AccessDeniedException("Teachers can only delete attendance on the lesson day.");
        }

        throw new AccessDeniedException("You are not allowed to delete this attendance.");
    }




}
