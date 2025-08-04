package uz.imv.lmssystem.dto.fildErrors.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO  {

    private int status;
    private String message;

}
