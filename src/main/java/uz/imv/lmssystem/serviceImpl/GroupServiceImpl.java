package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.GroupMapper;
import uz.imv.lmssystem.repository.CourseRepository;
import uz.imv.lmssystem.repository.GroupRepository;
import uz.imv.lmssystem.repository.RoomRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.GroupService;

/**
 * Created by Avazbek on 24/07/25 15:07
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupCreateResponse create(GroupCreateRequest dto) {

        Course course = courseRepository
                .findById(dto
                        .getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course with id : " + dto.getCourseId() + "not found!"));


        User teacher = userRepository
                .findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id : " + dto.getTeacherId() + "not found!"));

        Room room = roomRepository
                .findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room with id : " + dto.getRoomId() + "not found!"));


        Group group = new Group();

        group.setName(dto.getName());
        group.setStartDate(dto.getStartDate());
        group.setEndDate(dto.getEndDate());
        group.setStatus(GroupStatus.OPEN);
        group.setCourse(course);
        group.setTeacher(teacher);
        group.setRoom(room);
        group.setSchedule(dto.getSchedule());
        group.setStartDate(dto.getStartDate());
        group.setEndDate(dto.getEndDate());
        group.setLessonStartTime(dto.getLessonStartTime());
        group.setLessonEndTime(dto.getLessonEndTime());

        groupRepository.save(group);

        return groupMapper.groupToCreateResponse(group);
    }
}
