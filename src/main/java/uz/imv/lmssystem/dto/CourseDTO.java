package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.imv.lmssystem.entity.Course;

import java.io.Serializable;

/**
 * DTO for {@link Course}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements Serializable {
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "price cannot be null")
    @PositiveOrZero(message = "price must be positive or zero")
    private Long price;
}