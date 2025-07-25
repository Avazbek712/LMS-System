package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final HttpStatus status;
    public UserNotFoundException(Long id)  {
        super("User with id : " + id +  " not found");
        this.status = HttpStatus.NOT_FOUND;
    }

    public UserNotFoundException(String username)  {
        super("User with username : " + username +  " not found");
        this.status = HttpStatus.NOT_FOUND;
    }
}
