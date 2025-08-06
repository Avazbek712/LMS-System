package uz.imv.lmssystem.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.RoleDTO;
import uz.imv.lmssystem.dto.request.RoleRequestDTO;
import uz.imv.lmssystem.service.roles.RoleService;

/**
 * Created by Avazbek on 05/08/25 14:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@PreAuthorize("hasAuthority('ROLE_MANAGE')")
public class RoleController {


    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleRequestDTO dto) {

        return ResponseEntity.ok(roleService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

}
