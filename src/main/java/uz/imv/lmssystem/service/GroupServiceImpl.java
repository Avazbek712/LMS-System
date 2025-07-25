package uz.imv.lmssystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.request.GroupCreateRequestDTO;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.repository.GroupRepository;

import java.util.Objects;

/**
 * Created by Avazbek on 24/07/25 15:07
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public GroupCreateResponse create(GroupCreateRequestDTO group) {

        groupRepository
                .findByName(group.getName())
                .orElseThrow(()
                        -> new EntityAlreadyExistsException("Group with name : " + group.getName() + " is already exist"));
        return null;

    }
}
