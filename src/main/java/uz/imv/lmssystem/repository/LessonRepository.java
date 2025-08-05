package uz.imv.lmssystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.imv.lmssystem.entity.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT MAX(l.date) FROM Lesson l WHERE l.group.id = :groupId")
    Optional<LocalDate> findLatestLessonDateByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT l FROM Lesson l " +
            "WHERE l.group.room.id = :roomId " +   // Уроки должны быть в той же комнате
            "AND l.date = :date " +                 // и в тот же день
            "AND l.startTime < :endTime " +         // и существующий урок должен начаться ДО того, как закончится новый
            "AND l.endTime > :startTime")
        // и существующий урок должен закончиться ПОСЛЕ того, как начнется новый
    List<Lesson> findConflictingLessons(
            @Param("roomId") Long roomId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("SELECT l FROM Lesson l " +
            "WHERE l.group.teacher.id = :teacherId " + // Уроки должны быть у того же преподавателя
            "AND l.date = :date " +
            "AND l.startTime < :endTime " +
            "AND l.endTime > :startTime")
    List<Lesson> findConflictingLessonsForTeacher(
            @Param("teacherId") Long teacherId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    Page<Lesson> findByDate(LocalDate date , Pageable pageable);
}