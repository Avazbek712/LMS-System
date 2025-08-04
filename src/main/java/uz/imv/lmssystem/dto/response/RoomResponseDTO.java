package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Avazbek on 24/07/25 11:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO implements Serializable {

    private String name;

    private Long capacity;

    private Long desks;

    private Long chairs;



}
