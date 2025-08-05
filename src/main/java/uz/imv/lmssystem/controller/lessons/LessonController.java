package uz.imv.lmssystem.controller.lessons;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.service.lessons.LessonService;

import java.time.LocalDate;

/**
 * Created by Avazbek on 04/08/25 17:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {


    private final LessonService lessonService;

    @GetMapping("/to-the-day")
    @PreAuthorize("hasAuthority('LESSON_READ')")
    public ResponseEntity<?> lessonToTheDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(lessonService.lessonToTheDay(date, page, size));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('LESSON_READ')")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(lessonService.getAll(page, size));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('LESSON_READ')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getById(id));
    }

}
