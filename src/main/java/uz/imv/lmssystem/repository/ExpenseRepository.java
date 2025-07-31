package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.imv.lmssystem.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}