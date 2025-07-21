package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityUniqueException extends RuntimeException {

    private final HttpStatus status;

    public EntityUniqueException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
