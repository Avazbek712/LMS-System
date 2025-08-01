package uz.imv.lmssystem.serviceImpl;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.filter.StudentFilterDTO;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityAlreadyExistsException;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.StudentMapper;
import uz.imv.lmssystem.mapper.resolvers.GroupResolver;
import uz.imv.lmssystem.repository.GroupRepository;
import uz.imv.lmssystem.repository.StudentRepository;
import uz.imv.lmssystem.service.StudentService;
import uz.imv.lmssystem.specifications.StudentSpecification;
import uz.imv.lmssystem.utils.PageableUtil;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;
    private final GroupResolver groupResolver;
    private final StudentService studentService;

    @Override
    public StudentDTO getById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id : " + id + " not found!"));
        return studentMapper.toDTO(student);
    }

    @Override
    public PageableDTO getAll(Integer page, Integer size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<Student> students = studentPage.getContent();
        List<StudentDTO> studentDTOS = studentMapper.toDTO(students);
        if (students.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, null);
        }
        return new PageableDTO(studentPage.getSize(), studentPage.getTotalElements(), studentPage.getTotalPages(), !studentPage.isLast(), !studentPage.isFirst(), studentDTOS);
    }


    @Override
    @Transactional
    public StudentDTO save(StudentDTO dto) {
        if (studentRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new EntityAlreadyExistsException("Student with phone number : " + dto.getPhoneNumber() + " already exists!");
        }
        if (!groupRepository.existsById(dto.getGroupId())) {
            throw new EntityNotFoundException("Group with id : " + dto.getGroupId() + " not found!");
        }

        Student student = studentMapper.toEntity(dto, groupResolver);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    @Override
    @Transactional
    public StudentDTO update(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id : " + id + " not found!"));

        if (!student.getPhoneNumber().equals(dto.getPhoneNumber())
                && studentRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new EntityAlreadyExistsException("Student with phone number : " + dto.getPhoneNumber() + " already exists!");
        }

        if (!groupRepository.existsById(dto.getGroupId())) {
            throw new EntityNotFoundException("Group with id : " + dto.getGroupId() + " not found!");
        }

        studentMapper.updateEntity(dto, student, groupResolver);

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toDTO(updatedStudent);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {

        studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id : " + id + " not found!"));

        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public int resetExpiredPaymentStatuses() {

        final LocalDate today = LocalDate.now();

        return studentRepository.resetStatusForExpiredPayments(today);
    }

    @Override
    public PageableDTO getDebtors(Integer page, Integer size) {

    }

    @GetMapping("debtors")
    @PreAuthorize("hasAuthority('STUDENT_DEBTORS')")
    public ResponseEntity<PageableDTO> getDebtors(@Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(studentService.getDebtors(page,size));
    }

    @Override
    public Page<StudentDTO> getFilteredStudents(StudentFilterDTO filter, Pageable pageable) {
        Specification<Student> spec = StudentSpecification.filterBy(filter);
        return studentRepository.findAll(spec, pageable)
                .map(studentMapper::toDTO);
    }

    @Override
    public PageableDTO getFilteredStudentsAsPageableDTO(StudentFilterDTO filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> studentPage = getFilteredStudents(filter, pageable);
        return PageableUtil.mapToDTO(studentPage);
    }
}
