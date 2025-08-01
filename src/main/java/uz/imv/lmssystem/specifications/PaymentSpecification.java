package uz.imv.lmssystem.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.imv.lmssystem.dto.filter.PaymentFilterDTO;
import uz.imv.lmssystem.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {

    public static Specification<Payment> filterBy(PaymentFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getPaidAfter() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("paymentDate"), filter.getPaidAfter()));
            }

            if (filter.getPaidBefore() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("paymentDate"), filter.getPaidBefore()));
            }

            if (filter.getStudentId() != null) {
                predicates.add(cb.equal(root.get("student").get("id"), filter.getStudentId()));
            }

            if (filter.getCashierId() != null) {
                predicates.add(cb.equal(root.get("cashier").get("id"), filter.getCashierId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
