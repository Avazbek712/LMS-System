package uz.imv.lmssystem.controller.lessons;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.AttendanceDTO;
import uz.imv.lmssystem.dto.AttendanceStatusUpdateDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.service.AttendanceService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority()('ATTENDANCE_READ_ALL', 'ATTENDANCE_READ_OWN')")
    public PageableDTO getAll(@Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                              @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size,
                              @AuthenticationPrincipal User currentUser) {
        return attendanceService.getAll(page, size, currentUser);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ATTENDANCE_READ')")
    public ResponseEntity<AttendanceDTO> getById(@PathVariable("id") Long id, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(attendanceService.getById(id, currentUser));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ATTENDANCE_MARK')")
    public ResponseEntity<AttendanceDTO> save(@RequestBody AttendanceDTO attendanceDTO, @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(attendanceService.save(attendanceDTO, currentUser));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ATTENDANCE_UPDATE')")
    public ResponseEntity<AttendanceDTO> update(@PathVariable("id") Long id,
                                                @RequestBody AttendanceStatusUpdateDTO attendanceStatusUpdateDTO, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(attendanceService.updateStatus(id, attendanceStatusUpdateDTO, currentUser));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ATTENDANCE_DELETE')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id, @AuthenticationPrincipal User currentUser) {
        attendanceService.deleteById(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
