package uz.imv.lmssystem.controller.lessons;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.service.CourseService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAuthority('COURSE_READ')")
    public ResponseEntity<PageableDTO> getAll(@Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                                              @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(courseService.getAll(page, size));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('COURSE_READ')")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {

        return ResponseEntity.ok(courseService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COURSE_CREATE')")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody
                                                          CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.save(courseDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('COURSE_UPDATE')")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id,
                                                          @Valid @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.update(id, courseDTO));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('COURSE_DELETE')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
