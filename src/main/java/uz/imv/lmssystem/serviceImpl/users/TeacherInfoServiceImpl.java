package uz.imv.lmssystem.serviceImpl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.dto.TeacherInfoDTO;
import uz.imv.lmssystem.dto.request.TeacherInfoRequest;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.entity.TeacherInfo;
import uz.imv.lmssystem.entity.TeacherInfoMapper;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.repository.CourseRepository;
import uz.imv.lmssystem.repository.TeacherInfoRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.users.TeacherInfoService;

import java.util.List;


/**
 * Created by Avazbek on 06/08/25 16:44
 */
@Service
@RequiredArgsConstructor
public class TeacherInfoServiceImpl implements TeacherInfoService {


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TeacherInfoRepository teacherInfoRepository;
    private final TeacherInfoMapper teacherInfoMapper;

    @Override
    @CacheEvict(value = "groups_list", allEntries = true)
    public TeacherInfoDTO createInfo(TeacherInfoRequest dto) {


        User teacher = userRepository.findById(dto.getTeacherId()).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        TeacherInfo teacherInfo = new TeacherInfo();

        teacherInfo.setTeacher(teacher);
        teacherInfo.setCourse(course);

        teacherInfoRepository.save(teacherInfo);

        return new TeacherInfoDTO(
                teacher.getName(),
                teacher.getSurname(),
                course.getName()
        );

    }

    @Override
    @Cacheable(value = "teacher_info_list", key = "'teacher:' + #teacherId + ':page:' + #page + ':size:' + #size")
    public PageableDTO getInfo(Long teacherId, int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        Page<TeacherInfo> teacherInfoPage = teacherInfoRepository.findByTeacherId(teacher.getId(), pageable);

        List<TeacherInfo> teacherInfos = teacherInfoPage.getContent();

        if (teacherInfos.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, List.of());
        }

        List<TeacherInfoDTO> teacherInfoDTOS = teacherInfos.stream().map(teacherInfoMapper::toDTO).toList();

        return new PageableDTO(
                teacherInfoPage.getSize(),
                teacherInfoPage.getTotalElements(),
                teacherInfoPage.getTotalPages(),
                !teacherInfoPage.isLast(),
                !teacherInfoPage.isFirst(),
                teacherInfoDTOS
        );

    }

    @Override
    public TeacherInfoDTO getInfo(Long teacherInfoId) {

        TeacherInfo teacherInfo = teacherInfoRepository.findById(teacherInfoId).orElseThrow(() -> new EntityNotFoundException("information about teacher not found"));

        return teacherInfoMapper.toDTO(teacherInfo);
    }

    @Override
    @Cacheable(value = "teacher_info_list", key = "'page:' + #page + ':size:' + #size")
    public PageableDTO getAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TeacherInfo> teacherInfoPage = teacherInfoRepository.findAll(pageable);

        List<TeacherInfo> teacherInfos = teacherInfoPage.getContent();

        if (teacherInfos.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, List.of());
        }

        List<TeacherInfoDTO> teacherInfoDTOS = teacherInfos.stream().map(teacherInfoMapper::toDTO).toList();


        return new PageableDTO(
                teacherInfoPage.getSize(),
                teacherInfoPage.getTotalElements(),
                teacherInfoPage.getTotalPages(),
                !teacherInfoPage.isLast(),
                !teacherInfoPage.isFirst(),
                teacherInfoDTOS
        );
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "groups", key = "#id"),
            @CacheEvict(value = "groups_list", allEntries = true)
    })
    public void deleteById(Long id) {

        teacherInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        teacherInfoRepository.deleteById(id);
    }
}
