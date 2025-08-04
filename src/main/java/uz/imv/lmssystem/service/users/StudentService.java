package uz.imv.lmssystem.service.users;

import jakarta.transaction.Transactional;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.filter.StudentFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface StudentService {

    StudentDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    StudentDTO save(StudentDTO dto);

    StudentDTO update(Long id, StudentDTO dto);

    void deleteById(Long id);

    PageableDTO getFilteredStudents(StudentFilterDTO filter, int page, int size);

    PageableDTO getFilteredStudentsAsPageableDTO(StudentFilterDTO filter, int page, int size);

    @Transactional
    int resetExpiredPaymentStatuses();


    PageableDTO getDebtors(Integer page, Integer size);


}
