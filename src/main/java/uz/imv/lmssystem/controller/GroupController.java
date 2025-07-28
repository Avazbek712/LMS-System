package uz.imv.lmssystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.imv.lmssystem.dto.request.GroupCreateRequest;
import uz.imv.lmssystem.dto.response.GroupCreateResponse;
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
    ResponseEntity<GroupCreateResponse> create(@Valid @RequestBody GroupCreateRequest groupCreateRequest) {

        return ResponseEntity.ok(groupService.create(groupCreateRequest));
    }




}
