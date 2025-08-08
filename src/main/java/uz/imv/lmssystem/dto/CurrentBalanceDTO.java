package uz.imv.lmssystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Avazbek on 07/08/25 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBalanceDTO {

    private BigDecimal currentBalanceInBigDecimal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;


}
