package uz.imv.lmssystem.repository.lessons;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.imv.lmssystem.entity.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(@NotBlank(message = "name can't be blank and should be unique") String name);

    Optional<Room> findByRoomNumber(Short roomNumber);
}