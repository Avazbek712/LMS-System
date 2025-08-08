package uz.imv.lmssystem.repository.finances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Salary;

import java.math.BigDecimal;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

  @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Salary s")
  BigDecimal getTotalSalaries();

}