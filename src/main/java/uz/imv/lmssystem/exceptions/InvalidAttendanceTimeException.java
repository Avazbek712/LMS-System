package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidAttendanceTimeException extends RuntimeException {

    private final HttpStatus status;

    public InvalidAttendanceTimeException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;

    }
}
