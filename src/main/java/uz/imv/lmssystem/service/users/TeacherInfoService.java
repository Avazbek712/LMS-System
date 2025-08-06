package uz.imv.lmssystem.service.users;

import uz.imv.lmssystem.dto.TeacherInfoDTO;

public interface TeacherInfoService {
    TeacherInfoDTO createInfo(Long teacherId, Long courseId);
}
