package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CourseNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public CourseNotFoundException(Long id) {
        super("Course with ID: " + id + " was not found!");
        this.status = HttpStatus.NOT_FOUND;
    }

    public CourseNotFoundException(String name) {
        super("Course with name: " + name + " was not found!");
        this.status = HttpStatus.NOT_FOUND;
    }

}
