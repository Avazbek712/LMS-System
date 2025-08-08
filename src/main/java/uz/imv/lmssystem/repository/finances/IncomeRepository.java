package uz.imv.lmssystem.repository.finances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.imv.lmssystem.entity.Income;

import java.math.BigDecimal;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i")
    BigDecimal getTotalIncome();
}