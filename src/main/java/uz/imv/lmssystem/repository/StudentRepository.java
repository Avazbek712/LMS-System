package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.imv.lmssystem.entity.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    boolean existsByPhoneNumber(String phoneNumber);


    @Modifying
    @Query("UPDATE Student s " +
            "SET s.paymentStatus = false " +
            "WHERE s.paymentStatus = true " +
            "AND s.paidUntilDate < :currentDate")
    int resetStatusForExpiredPayments(@Param("currentDate") LocalDate currentDate);
}