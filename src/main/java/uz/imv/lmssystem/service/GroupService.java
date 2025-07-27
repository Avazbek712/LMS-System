package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;

public interface GroupService {
    GroupCreateResponse create(GroupCreateRequest group);



}
