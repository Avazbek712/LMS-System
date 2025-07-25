package uz.imv.lmssystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.mapper.CourseMapper;
import uz.imv.lmssystem.exceptions.CourseNotFoundException;
import uz.imv.lmssystem.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Avazbek on 24/07/25 10:42
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {


    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDTO getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        return new CourseDTO(course.getName(), course.getPrice());
    }

    @Override
    public List<CourseDTO> getAll() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CourseResponseDTO save(CourseDTO dto) {

        courseRepository.findByName(dto.getName()).ifPresent(c -> {
            throw new EntityAlreadyExistsException("Course with name : " + dto.getName() + " already exist!");
        });



        Course course = new Course();

        course.setName(dto.getName());
        course.setPrice(dto.getPrice());

        courseRepository.save(course);

        return new CourseResponseDTO(course.getName(), course.getPrice());
    }

    @Override
    public CourseResponseDTO update(Long id, CourseDTO dto) {

        courseRepository.findByName(dto.getName()).ifPresent(c -> {
            if (!c.getId().equals(id))
                throw new EntityAlreadyExistsException("Course with name : " + dto.getName() + " already exist!");
        });

        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        course.setName(dto.getName());
        course.setPrice(dto.getPrice());

        courseRepository.save(course);

        return new CourseResponseDTO(course.getName(), course.getPrice());
    }

    @Override
    public void deleteById(Long id) {

        courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        courseRepository.deleteById(id);

    }
}
