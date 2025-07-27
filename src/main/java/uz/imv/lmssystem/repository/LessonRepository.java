package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.imv.lmssystem.entity.Lesson;

import java.time.LocalDate;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
  Optional<LocalDate> findLatestLessonDateByGroupId(Long id);
}