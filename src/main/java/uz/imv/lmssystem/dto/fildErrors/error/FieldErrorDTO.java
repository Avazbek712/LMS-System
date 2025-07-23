package uz.imv.lmssystem.dto.fildErrors.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorDTO {
    private String field;
    private String message;
}
