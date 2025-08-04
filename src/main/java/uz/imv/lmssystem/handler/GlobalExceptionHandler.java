package uz.imv.lmssystem.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uz.imv.lmssystem.dto.fildErrors.error.ErrorDTO;
import uz.imv.lmssystem.dto.fildErrors.error.ErrorFieldsKeeperDTO;
import uz.imv.lmssystem.dto.fildErrors.error.FieldErrorDTO;
import uz.imv.lmssystem.enums.AttendanceStatus;
import uz.imv.lmssystem.exceptions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice(basePackages = "uz.imv")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(FileNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ScheduleConflictException.class)
    public ResponseEntity<ErrorDTO> handle(ScheduleConflictException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handle(EntityAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = InvalidAttendanceTimeException.class)
    public ResponseEntity<ErrorDTO> handle(InvalidAttendanceTimeException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = UnknownRoleException.class)
    public ResponseEntity<ErrorDTO> handle(UnknownRoleException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ErrorDTO> handle(UserAlreadyExistException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }


    @ExceptionHandler(value = EntityUniqueException.class)
    public ResponseEntity<ErrorDTO> handleEntityUniqueException(EntityUniqueException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = EntityNotDeleteException.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotDeleteException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handle(AccessDeniedException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleInvalidEnum(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        if (message != null && message.contains("AttendanceStatus")) {
            ErrorDTO errorDTO = new ErrorDTO(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid attendance status provided. Allowed values: PRESENT, ABSENT, LATE, SICK"
            );
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        ErrorDTO fallback = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request body."
        );
        return new ResponseEntity<>(fallback, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFieldsKeeperDTO> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldErrorDTO> fieldErrors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            fieldErrors.add(new FieldErrorDTO(fieldName, message));
        }
        ErrorFieldsKeeperDTO errorDTO = new ErrorFieldsKeeperDTO(
                400,
                "field not valid",
                fieldErrors
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handle(IllegalArgumentException e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> handle() {
        ErrorDTO errorDTO = new ErrorDTO(
                500,
                "Server error please try again later"
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
