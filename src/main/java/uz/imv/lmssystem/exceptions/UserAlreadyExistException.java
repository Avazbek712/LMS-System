package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistException extends RuntimeException {

    private final HttpStatus status;

    public UserAlreadyExistException(String email) {
        super("User with email : " + email + " already exists");
        this.status = HttpStatus.BAD_REQUEST;

    }
}
