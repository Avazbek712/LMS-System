package uz.imv.lmssystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.GroupDTO;
import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.GroupService;

/**
 * Created by Avazbek on 27/07/25 22:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {


    private final GroupService groupService;

    @PostMapping
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    ResponseEntity<GroupCreateResponse> create(@Valid @RequestBody GroupCreateRequest groupCreateRequest) {

        return ResponseEntity.ok(groupService.create(groupCreateRequest));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority(' GROUP_READ')")
    ResponseEntity<GroupDTO> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(groupService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('GROUP_READ')")
    ResponseEntity<PageableDTO> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(groupService.getAll(page, size));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('GROUP_DELETE')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        groupService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("own")
    @PreAuthorize("hasAuthority('GROUP_READ_OWN')")
    ResponseEntity<PageableDTO> getMyGroups(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(groupService.getMyGroups(user, page, size));
    }


}
