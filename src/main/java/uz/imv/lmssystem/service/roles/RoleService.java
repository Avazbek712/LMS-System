package uz.imv.lmssystem.service.roles;

import uz.imv.lmssystem.dto.RoleDTO;
import uz.imv.lmssystem.dto.request.RoleRequestDTO;

public interface RoleService {

    RoleDTO create(RoleRequestDTO dto);


    void delete(Long id);
}
