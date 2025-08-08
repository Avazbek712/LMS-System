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
import uz.imv.lmssystem.enums.ExpenseCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Uzbek on 28/07/25 14:52
 */
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction(value = "deleted=false")
@SQLDelete(sql = "UPDATE expense SET deleted = true WHERE id = ?")
public class Expense extends AbsLongEntity {

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
    private ExpenseCategoryEnum category;
}
