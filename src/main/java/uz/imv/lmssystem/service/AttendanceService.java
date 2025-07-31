package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.dto.AttendanceStatusUpdateDTO;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface AttendanceService {

    AttendanceDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    AttendanceDTO save(AttendanceDTO dto);

    AttendanceDTO update(Long id, AttendanceDTO dto);
        AttendanceDTO updateStatus(Long id, AttendanceStatusUpdateDTO dto);

    void deleteById(Long id);


}
