package uz.imv.lmssystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.imv.lmssystem.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByStudentIdAndLessonId(Long studentId, Long lessonId);

    Page<Attendance> findAllByLesson_Group_Teacher_Id(Long id, Pageable pageable);
}