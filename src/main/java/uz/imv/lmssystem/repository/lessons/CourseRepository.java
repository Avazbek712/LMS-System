package uz.imv.lmssystem.repository.lessons;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> getByName(@NotBlank(message = "name cannot be blank") String name);

    Optional<Course> findByName(@NotBlank(message = "name cannot be blank") String name);
}