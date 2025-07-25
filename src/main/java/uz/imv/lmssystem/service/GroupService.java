package uz.imv.lmssystem.service;

import uz.imv.lmssystem.dto.request.GroupCreateRequestDTO;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;

public interface GroupService {


    GroupCreateResponse create(GroupCreateRequestDTO group);



}
