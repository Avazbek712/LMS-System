package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Avazbek on 24/07/25 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO implements Serializable {

    private String name;

    private BigDecimal price;
}
