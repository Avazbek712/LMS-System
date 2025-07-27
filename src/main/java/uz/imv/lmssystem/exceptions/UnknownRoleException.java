package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnknownRoleException extends RuntimeException {

    private final HttpStatus status;

    public UnknownRoleException(Long roleName) {
        super("Role with id : " + roleName + "not found!");
        this.status = HttpStatus.NOT_FOUND;
    }
}
