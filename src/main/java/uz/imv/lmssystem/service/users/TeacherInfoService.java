package uz.imv.lmssystem.service.users;

import uz.imv.lmssystem.dto.TeacherInfoDTO;
import uz.imv.lmssystem.dto.request.TeacherInfoRequest;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface TeacherInfoService {
    TeacherInfoDTO createInfo(TeacherInfoRequest teacherInfoRequest);

    PageableDTO getInfo(Long teacherId, int page, int size);

    TeacherInfoDTO getInfo(Long teacherInfoId);

    PageableDTO getAll(int page, int size);

    void deleteById(Long id);
}
