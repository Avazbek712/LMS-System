package uz.imv.lmssystem.controller.users;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.filter.StudentFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.service.PaymentService;
import uz.imv.lmssystem.service.StudentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public PageableDTO getAll(@Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                              @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {

        return studentService.getAll(page, size);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT_READ_DETAILS', 'STUDENT_READ')")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT_CREATE')")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.save(studentDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("balance/{id}")
    @PreAuthorize("hasAuthority('STUDENT_BALANCE')")
    public ResponseEntity<?> checkStatus(@PathVariable Long id) {

        return ResponseEntity.ok(paymentService.checkPaymentStatus(id));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<PageableDTO> filterStudents(@ModelAttribute StudentFilterDTO filter,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getFilteredStudentsAsPageableDTO(filter, page, size));
    }

}
