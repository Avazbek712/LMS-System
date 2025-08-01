package uz.imv.lmssystem.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Group;
import uz.imv.lmssystem.enums.GroupStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    List<Group> findAllByStatusAndEndDateAfter(GroupStatus groupStatus, LocalDate now);

    Page<Group> findAllByTeacherId(Long id, Pageable pageable);
}