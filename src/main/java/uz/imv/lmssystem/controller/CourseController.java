package uz.imv.lmssystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.CourseDTO;
import uz.imv.lmssystem.dto.response.CourseResponseDTO;
import uz.imv.lmssystem.service.CourseService;

import java.util.List;

/**
 * Created by Avazbek on 24/07/25 10:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('COURSE_READ')")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {

        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('COURSE_READ')")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {

        return ResponseEntity.ok(courseService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('COURSE_CREATE')")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody
                                                          CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.save(courseDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('COURSE_UPDATE')")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id,
                                                          @Valid @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.update(id, courseDTO));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('COURSE_DELETE')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
