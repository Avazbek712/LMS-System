package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.TeacherInfo;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {
}