package uz.imv.lmssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableDTO implements Serializable {

    private Integer size;

    private Long totalElements;

    private Integer totalPages;

    private boolean hasNext;

    private boolean hasPrevious;

    private Object object;

}