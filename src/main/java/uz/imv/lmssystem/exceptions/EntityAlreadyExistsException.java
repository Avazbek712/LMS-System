package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public EntityAlreadyExistsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
