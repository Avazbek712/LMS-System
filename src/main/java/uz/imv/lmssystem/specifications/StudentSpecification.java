package uz.imv.lmssystem.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.imv.lmssystem.dto.filter.StudentFilterDTO;
import uz.imv.lmssystem.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterBy(StudentFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFullName() != null && !filter.getFullName().isBlank()) {
                String[] words = filter.getFullName().toLowerCase().split(" ");
                List<Predicate> namePredicates = new ArrayList<>();
                for (String word : words) {
                    String pattern = "%" + word + "%";
                    namePredicates.add(cb.like(cb.lower(root.get("firstName")), pattern));
                    namePredicates.add(cb.like(cb.lower(root.get("lastName")), pattern));
                }
                predicates.add(cb.or(namePredicates.toArray(new Predicate[0])));
            }

            if (filter.getGroupId() != null) {
                predicates.add(cb.equal(root.get("group").get("id"), filter.getGroupId()));
            }

            if (filter.getPaymentStatus() != null) {
                predicates.add(cb.equal(root.get("hasPaid"), filter.getPaymentStatus())); // поле может быть boolean
            }

            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isBlank()) {
                String pattern = "%" + filter.getPhoneNumber() + "%";
                predicates.add(cb.like(root.get("phoneNumber"), pattern));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
