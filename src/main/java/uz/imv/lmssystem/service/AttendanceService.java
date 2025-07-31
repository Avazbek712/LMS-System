package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.dto.AttendanceStatusUpdateDTO;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;

public interface AttendanceService {

    AttendanceDTO getById(Long id, User currentUser);

    PageableDTO getAll(Integer page, Integer size, User currentUser);

    AttendanceDTO save(AttendanceDTO dto,User currentUser);

    AttendanceDTO update(Long id, AttendanceDTO dto,User currentUser);
        AttendanceDTO updateStatus(Long id, AttendanceStatusUpdateDTO dto,User currentUser);

    void deleteById(Long id,User currentUser);


}
