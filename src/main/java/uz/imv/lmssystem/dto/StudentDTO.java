package uz.imv.lmssystem.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.imv.lmssystem.entity.Student}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {

    @Size(min = 3)
    private String name;

    @Size(min = 3)
    private String surname;

    private String phoneNumber;

    private Long groupId;
}