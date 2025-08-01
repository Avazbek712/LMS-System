package uz.imv.lmssystem.utils;

import org.springframework.data.domain.Page;
import uz.imv.lmssystem.dto.response.PageableDTO;

public class PageableUtil {

    public static <T> PageableDTO mapToDTO(Page<T> page) {
        return new PageableDTO(
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious(),
                page.getContent()
        );
    }
}
