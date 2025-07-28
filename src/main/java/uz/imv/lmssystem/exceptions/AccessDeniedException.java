package uz.imv.lmssystem.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AccessDeniedException extends RuntimeException {

    private HttpStatus status = HttpStatus.FORBIDDEN;

    public AccessDeniedException(String message) {
        super(message);
        HttpStatus status = this.status;
    }
}
