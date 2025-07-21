package uz.imv.lmssystem.exceptions;

public class RestException extends RuntimeException {

    public RestException(String message) {
        super(message);
    }

    public static RestException create(String message) {
        return new RestException(message);
    }
}
