package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Room}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements Serializable {

    @NotBlank(message = "name can't be blank and should be unique")
    private String name;

    @Min(message = "capacity must be greater than or equal to 2", value = 2)
    private int capacity;

    @Min(message = "desks must be greater than or equal to 2", value = 2)
    private Integer desks;

    @Min(message = "chairs must be greater than or equal to 2", value = 2)
    private Integer chairs;

    @Positive(message = "room number must be positive")
    private Short roomNumber;
}