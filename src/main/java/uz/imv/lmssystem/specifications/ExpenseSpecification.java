package uz.imv.lmssystem.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.imv.lmssystem.dto.filter.ExpenseFilterDTO;
import uz.imv.lmssystem.entity.Expense;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {

    public static Specification<Expense> filterBy(ExpenseFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), filter.getFromDate()));
            }

            if (filter.getToDate() != null) {
                LocalDateTime toDateEndOfDay = filter.getToDate()
                        .withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), toDateEndOfDay));
            }

            if (filter.getMinAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount()));
            }

            if (filter.getMaxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));
            }

            if (filter.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
