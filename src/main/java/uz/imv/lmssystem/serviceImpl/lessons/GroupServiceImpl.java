package uz.imv.lmssystem.serviceImpl.lessons;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.GroupDTO;
import uz.imv.lmssystem.dto.filter.GroupFilterDTO;
import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Course;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.entity.Room;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.GroupStatus;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.GroupMapper;
import uz.imv.lmssystem.repository.*;
import uz.imv.lmssystem.service.lessons.GroupService;
import uz.imv.lmssystem.specifications.GroupSpecification;

import java.util.List;

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
    @Transactional
    @CacheEvict(value = "groups_list", allEntries = true)
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

    @Override
    public GroupDTO getById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group with id : " + id + "not found!"));

        return groupMapper.toDTO(group);

    }

    @Override
    @Cacheable(value = "groups_list", key = "'page:' + #page + ':size:' + #size")
    public PageableDTO getAll(Integer page, Integer size) {

        System.out.println("=== Идет запрос к бд!!!!!");
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Group> groups = groupRepository.findAll(pageable);
        List<Group> content = groups.getContent();

        if (content.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, null);
        }

        List<GroupDTO> courseDTOS = content.stream().map(groupMapper::toDTO).toList();

        return new PageableDTO(
                groups.getSize(),
                groups.getTotalElements(),
                groups.getTotalPages(),
                !groups.isLast(),
                !groups.isFirst(),
                courseDTOS
        );
    }


    @Override
    @Transactional
        @Caching(evict = {
            @CacheEvict(value = "groups", key = "#id"),
            @CacheEvict(value = "groups_list", allEntries = true)
    })
    public void deleteById(Long id) {

        if (!groupRepository.existsById(id)) throw new EntityNotFoundException("Group with id : " + id + " not found!");

        groupRepository.deleteById(id);
    }

    @Override
    public PageableDTO getFilteredGroups(GroupFilterDTO filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Group> spec = GroupSpecification.filterBy(filter);
        Page<Group> groupPage = groupRepository.findAll(spec, pageable);
        List<GroupDTO> groupDTOS = groupPage.map(groupMapper::toDTO).getContent();

        return new PageableDTO(
                groupPage.getSize(),
                groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupPage.hasNext(),
                groupPage.hasPrevious(),
                groupDTOS
        );
    }


    @Override
    @Cacheable(value = "groups_list", key = "'page:' + #page + ':size:' + #size")
    public PageableDTO getMyGroups(User user, Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Group> groups = groupRepository.findAllByTeacherId(user.getId(), pageable);
        List<Group> content = groups.getContent();

        if (content.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, null);
        }


        List<GroupDTO> groupDTOs = content.stream().map(groupMapper::toDTO).toList();


        return new PageableDTO(
                groups.getSize(),
                groups.getTotalElements(),
                groups.getTotalPages(),
                !groups.isLast(),
                !groups.isFirst(),
                groupDTOs
        );
    }
}
