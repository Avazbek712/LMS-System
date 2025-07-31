package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Payment;
import uz.imv.lmssystem.entity.Student;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p")
    BigDecimal getTotalPayments();

    @Query("SELECT p.student FROM Payment p WHERE p.id = :id")
    Student getStudentById(Long id);


}