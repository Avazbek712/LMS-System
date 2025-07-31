package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.CompanyBalance;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CompanyBalanceRepository extends JpaRepository<CompanyBalance, Long> {

    @Query("SELECT cw.initialCapital FROM CompanyBalance cw WHERE cw.id = 1")
    Optional<BigDecimal> getInitialBalance();

}