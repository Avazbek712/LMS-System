package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Avazbek on 30/07/25 09:28
 */
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update salary set deleted=true where id=?")
public class Salary extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User employee;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @CreationTimestamp
    private LocalDate date;
}
