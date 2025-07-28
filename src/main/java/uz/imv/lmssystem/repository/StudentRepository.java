package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.imv.lmssystem.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    List<Student> findAllByGroupId(Long groupId);
}