package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.GroupDTO;
import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;

public interface GroupService {
    GroupCreateResponse create(GroupCreateRequest group);

    GroupDTO getById(Long id);

    PageableDTO getAll(Integer page, Integer size);


    void deleteById(Long id);

    PageableDTO getMyGroups(User user, Integer page, Integer size);
}
