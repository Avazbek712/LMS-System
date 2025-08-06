package uz.imv.lmssystem.serviceImpl.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.RoleDTO;
import uz.imv.lmssystem.dto.request.RoleRequestDTO;
import uz.imv.lmssystem.entity.Role;
import uz.imv.lmssystem.exceptions.RoleNotFoundException;
import uz.imv.lmssystem.repository.RoleRepository;
import uz.imv.lmssystem.service.roles.RoleService;

/**
 * Created by Avazbek on 05/08/25 14:38
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleDTO create(RoleRequestDTO dto) {

        roleRepository.findByName(dto.getRoleName()).orElseThrow(() -> new RoleNotFoundException("Role with name " + dto.getRoleName() + " not found"));

        Role role = new Role();

        role.setName(dto.getRoleName());
        role.setPermissions(dto.getPermissions());
        roleRepository.save(role);

        return new RoleDTO(
                role.getName(),
                dto.getDescription(),
                role.getPermissions()
        );
    }

    @Override
    public void delete(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role with id " + id + " not found"));

        roleRepository.delete(role);
    }

}
