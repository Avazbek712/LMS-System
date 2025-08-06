package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyFileException extends RuntimeException {

    private final HttpStatus status;

    public EmptyFileException() {
        super("File is empty!");
        this.status = HttpStatus.BAD_REQUEST;
    }
}
