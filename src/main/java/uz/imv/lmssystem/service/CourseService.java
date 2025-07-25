package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    CourseDTO getById(Long id);

    List<CourseDTO> getAll();

    CourseResponseDTO save(CourseDTO dto);

    CourseResponseDTO update(Long id, CourseDTO dto);

    void deleteById(Long id);


}
