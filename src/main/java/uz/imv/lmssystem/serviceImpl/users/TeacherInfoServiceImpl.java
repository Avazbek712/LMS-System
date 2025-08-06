package uz.imv.lmssystem.serviceImpl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.TeacherInfoDTO;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.entity.TeacherInfo;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.repository.CourseRepository;
import uz.imv.lmssystem.repository.TeacherInfoRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.users.TeacherInfoService;


/**
 * Created by Avazbek on 06/08/25 16:44
 */
@Service
@RequiredArgsConstructor
public class TeacherInfoServiceImpl implements TeacherInfoService {


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TeacherInfoRepository teacherInfoRepository;

    @Override
    public TeacherInfoDTO createInfo(Long teacherId, Long courseId) {


        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

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




}
