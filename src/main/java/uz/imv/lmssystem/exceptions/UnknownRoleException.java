package uz.imv.lmssystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnknownRoleException extends RuntimeException {

    private final HttpStatus status;

    public UnknownRoleException(String roleName) {
        super("Role with name : " + roleName + " does not exist!");
        this.status = HttpStatus.NOT_FOUND;
    }
}
