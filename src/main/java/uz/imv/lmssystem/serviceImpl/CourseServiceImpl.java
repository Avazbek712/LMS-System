package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.CourseNotFoundException;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.mapper.CourseMapper;
import uz.imv.lmssystem.repository.CourseRepository;
import uz.imv.lmssystem.service.CourseService;

import java.util.List;


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
    public PageableDTO getAll(Integer page, Integer size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<Course> courses = coursePage.getContent();
        List<CourseDTO> courseDTOS = courseMapper.toDTO(courses);
        return new PageableDTO(
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                !coursePage.isLast(),
                !coursePage.isFirst(),
                courseDTOS
        );
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

        courseMapper.updateEntity(dto, course);

        courseRepository.save(course);

        return new CourseResponseDTO(course.getName(), course.getPrice());
    }

    @Override
    public void deleteById(Long id) {

        courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        courseRepository.deleteById(id);
    }
}
