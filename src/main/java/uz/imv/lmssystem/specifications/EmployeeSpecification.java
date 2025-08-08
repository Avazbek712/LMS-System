package uz.imv.lmssystem.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.imv.lmssystem.dto.filter.UserFilterDTO;
import uz.imv.lmssystem.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<User> filterBy(UserFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFullName() != null && !filter.getFullName().isBlank()) {
                String[] words = filter.getFullName().toLowerCase().split(" ");
                List<Predicate> namePredicates = new ArrayList<>();
                for (String word : words) {
                    String pattern = "%" + word + "%";
                    namePredicates.add(cb.like(cb.lower(root.get("name")), pattern));
                    namePredicates.add(cb.like(cb.lower(root.get("surname")), pattern));
                }
                predicates.add(cb.or(namePredicates.toArray(new Predicate[0])));
            }

            if (filter.getUsername() != null && !filter.getUsername().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("username")), filter.getUsername().toLowerCase()));
            }

            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isBlank()) {
                predicates.add(cb.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
            }

            if (filter.getRole() != null && !filter.getRole().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.join("role").get("name")), filter.getRole().toLowerCase()));
            }

            if (filter.getDeleted() != null) {
                predicates.add(cb.equal(root.get("deleted"), filter.getDeleted()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}