package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends RuntimeException {

  private final HttpStatus status;

    public InvalidTokenException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
