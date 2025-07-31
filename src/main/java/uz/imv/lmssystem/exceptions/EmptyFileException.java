package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class EmptyFileException extends RuntimeException {

    private final HttpStatus status;

    public EmptyFileException() {
        super("File is empty!");
        this.status = HttpStatus.BAD_REQUEST;
    }
}
