package uz.imv.lmssystem.mapper.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.repository.GroupRepository;

@Component
@RequiredArgsConstructor
public class GroupResolver {

    private final GroupRepository groupRepository;

    public Group resolve(Long groupId) {
        if (groupId == null) return null;
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
    }
}
