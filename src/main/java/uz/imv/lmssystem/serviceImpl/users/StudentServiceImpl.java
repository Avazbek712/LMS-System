package uz.imv.lmssystem.serviceImpl.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.imv.lmssystem.dto.StudentDTO;
import uz.imv.lmssystem.dto.StudentDebtors;
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

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;
    private final GroupResolver groupResolver;


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

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> debtors = studentRepository.findByPaymentStatus(false, pageable);
        List<Student> content = debtors.getContent();

        if (debtors.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, null);
        }
        List<StudentDebtors> studentDTOS = content.stream()
                .map(studentMapper::toDebtors)
                .toList();

        return new PageableDTO(
                debtors.getSize(),
                debtors.getTotalElements(),
                debtors.getTotalPages(),
                !debtors.isLast(),
                !debtors.isFirst(),
                studentDTOS);
    }


    @Override
    public PageableDTO getFilteredStudents(StudentFilterDTO filter, int page, int size) {
        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Student> spec = StudentSpecification.filterBy(filter);
        Page<Student> studentPage = studentRepository.findAll(spec, pageable);
        List<Student> students = studentPage.getContent();

        if (students.isEmpty()) {
            return new PageableDTO(size, 0L, 0, false, false, List.of());
        }
        List<StudentDTO> studentDTOs = students.stream().map(studentMapper::toDTO).toList();

        return new PageableDTO(
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                !studentPage.isLast(),
                !studentPage.isFirst(),
                studentDTOs
        );

    }

    @Override
    public PageableDTO getFilteredStudentsAsPageableDTO(StudentFilterDTO filter, int page, int size) {
        return getFilteredStudents(filter, page, size);
    }
}
