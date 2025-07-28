package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {



}
