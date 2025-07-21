package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public EntityNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }
}
