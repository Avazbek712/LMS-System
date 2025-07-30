package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;
import uz.imv.lmssystem.enums.IncomeCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 29/07/25 10:54
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction("deleted=false")
@SQLDelete(sql = "update income set deleted=true where id=?")
public class Income extends AbsLongEntity {

    @Column(columnDefinition = "text")
    private String description;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User employee;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncomeCategoryEnum category;
}
