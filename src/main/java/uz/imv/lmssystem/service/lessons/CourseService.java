package uz.imv.lmssystem.service.lessons;

import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

public interface CourseService {

    CourseDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    CourseResponseDTO save(CourseDTO dto);

    CourseResponseDTO update(Long id, CourseDTO dto);

    void deleteById(Long id);


}
