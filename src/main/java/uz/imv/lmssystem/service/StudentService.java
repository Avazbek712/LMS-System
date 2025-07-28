package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface StudentService {

    StudentDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    StudentDTO save(StudentDTO dto);

    StudentDTO update(Long id, StudentDTO dto);

    void deleteById(Long id);


}
