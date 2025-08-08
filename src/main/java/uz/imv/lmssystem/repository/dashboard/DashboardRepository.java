package uz.imv.lmssystem.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.projection.DashboardStatsProjection;

@Repository
public interface DashboardRepository extends JpaRepository<Student, Long> {
    @Query(value = """
            SELECT
                (SELECT COUNT(u.id) FROM users u WHERE u.deleted = false) AS employeeCount,
            
                (SELECT COUNT(s.id) FROM Student s JOIN s.group g
                 WHERE s.deleted = false AND g.deleted = false AND g.status = 'OPEN') AS activeStudentCount,
            
                (SELECT COUNT(g.id) FROM groups g WHERE g.deleted = false AND g.status = 'OPEN') AS activeGroupCount,
            
                (SELECT COUNT(s.id) FROM Student s JOIN s.group g
                 WHERE s.deleted = false AND s.paymentStatus = false AND g.status = 'OPEN') AS debtorStudentCount,
            
                (SELECT COUNT(DISTINCT p.student.id) FROM Payment p
                 WHERE FUNCTION('date_trunc', 'month', p.paymentDate) = FUNCTION('date_trunc', 'month', CURRENT_DATE)) AS paidInCurrentMonthCount
            """)
    DashboardStatsProjection getDashboardStatistics();


}
