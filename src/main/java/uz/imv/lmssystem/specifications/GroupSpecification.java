package uz.imv.lmssystem.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.imv.lmssystem.dto.filter.GroupFilterDTO;
import uz.imv.lmssystem.entity.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupSpecification {

    public static Specification<Group> filterBy(GroupFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getTeacherId() != null) {
                predicates.add(cb.equal(root.get("teacher").get("id"), filter.getTeacherId()));
            }

            if (filter.getCourseId() != null) {
                predicates.add(cb.equal(root.get("course").get("id"), filter.getCourseId()));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}