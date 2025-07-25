package uz.imv.lmssystem.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.RoomDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.dto.response.RoomResponseDTO;
import uz.imv.lmssystem.service.RoomService;

import java.util.List;

/**
 * Created by Avazbek on 24/07/25 12:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {


    private final RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROOM_READ')")
    public PageableDTO getAll(@Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                              @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {

        return roomService.getAll(page, size);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROOM_READ')")
    public ResponseEntity<RoomDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROOM_CREATE')")
    public ResponseEntity<RoomResponseDTO> save(@RequestBody RoomDTO roomDTO) {

        return ResponseEntity.ok(roomService.save(roomDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROOM_UPDATE')")
    public ResponseEntity<RoomResponseDTO> update(@PathVariable("id") Long id,
                                                  @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.update(id, roomDTO));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROOM_DELETE')")
    public ResponseEntity<Long> deleteById(@PathVariable("id") Long id) {
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
