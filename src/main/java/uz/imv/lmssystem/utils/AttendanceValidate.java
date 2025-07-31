package uz.imv.lmssystem.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Attendance;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.PermissionsEnum;
import uz.imv.lmssystem.exceptions.AccessDeniedException;
import uz.imv.lmssystem.service.security.AuthService;

@Component
@RequiredArgsConstructor
public class AttendanceValidate {


    private final AuthService authService;

    public void checkTeacherAccessToAttendance(Attendance attendance) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = (User) authService.loadUserByUsername(username);

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

}
