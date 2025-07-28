package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScheduleConflictException extends RuntimeException {


    private final HttpStatus status;

    public ScheduleConflictException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }
}
