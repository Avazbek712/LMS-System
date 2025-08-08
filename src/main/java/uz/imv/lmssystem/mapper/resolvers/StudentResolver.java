package uz.imv.lmssystem.mapper.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.repository.users.StudentRepository;

@Component
@RequiredArgsConstructor
public class StudentResolver {
    private final StudentRepository studentRepository;

    public Student resolve(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
    }
}

