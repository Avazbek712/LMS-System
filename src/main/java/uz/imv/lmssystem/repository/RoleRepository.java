package uz.imv.lmssystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(name = "select * from roles where name=?", nativeQuery = true)
    Optional<Role> findByName(String name);


}