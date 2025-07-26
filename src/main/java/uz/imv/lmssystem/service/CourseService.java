package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;

import java.util.List;

public interface CourseService {

    CourseDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);

    CourseResponseDTO save(CourseDTO dto);

    CourseResponseDTO update(Long id, CourseDTO dto);

    Long deleteById(Long id);


}
