package uz.imv.lmssystem.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.filter.StudentFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;


import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    StudentDTO save(StudentDTO dto);

    StudentDTO update(Long id, StudentDTO dto);

    void deleteById(Long id);

    Page<StudentDTO> getFilteredStudents(StudentFilterDTO filter, Pageable pageable);

    PageableDTO getFilteredStudentsAsPageableDTO(StudentFilterDTO filter, int page, int size);

    @Transactional
    int resetExpiredPaymentStatuses();


    PageableDTO getDebtors(Integer page, Integer size);


}
