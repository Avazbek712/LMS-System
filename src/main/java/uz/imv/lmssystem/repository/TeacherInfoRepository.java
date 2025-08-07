package uz.imv.lmssystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.TeacherInfo;

import java.util.List;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {

    Page<TeacherInfo> findByTeacherId(Long id, Pageable pageable);
}