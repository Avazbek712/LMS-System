package uz.imv.lmssystem.controller.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.TeacherInfoDTO;
import uz.imv.lmssystem.dto.request.TeacherInfoRequest;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.service.users.TeacherInfoService;

/**
 * Created by Avazbek on 07/08/25 10:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher-info")
public class TeacherInfoController {


    private final TeacherInfoService teacherInfoService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER_INFO_CREATE')")
    public ResponseEntity<TeacherInfoDTO> createInfo(@Valid @RequestBody TeacherInfoRequest request) {

        return ResponseEntity.ok(teacherInfoService.createInfo(request));
    }

    @GetMapping("all-info/{teacherId}")
    @PreAuthorize("hasAuthority('TEACHER_INFO_READ')")
    public ResponseEntity<PageableDTO> getInfo(@PathVariable Long teacherId,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(teacherInfoService.getInfo(teacherId, page, size));
    }

    @GetMapping("{teacherInfoId}")
    @PreAuthorize("hasAuthority('TEACHER_INFO_READ')")
    public ResponseEntity<TeacherInfoDTO> getInfo(@PathVariable Long teacherInfoId) {
        return ResponseEntity.ok(teacherInfoService.getInfo(teacherInfoId));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('TEACHER_INFO_READ')")
    public ResponseEntity<PageableDTO> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(teacherInfoService.getAll(page, size));
    }

}
