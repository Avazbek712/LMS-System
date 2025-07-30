package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Avazbek on 24/07/25 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {

    private String name;

    private BigDecimal price;
}
