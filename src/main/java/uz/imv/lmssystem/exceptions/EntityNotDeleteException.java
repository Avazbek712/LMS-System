package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotDeleteException extends RuntimeException {

    private final HttpStatus status;

    public EntityNotDeleteException(String number, HttpStatus status) {
        super(number);
        this.status = status;
    }
}
