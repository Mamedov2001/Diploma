package kz.careerguidance.util;

import kz.careerguidance.util.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
//        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError fieldError : errors) {
            throw new NotFoundException(fieldError.getDefaultMessage());
        }
//        errors.forEach(error -> errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage()).append("; "));
//            throw new NotFoundException(errorMessage.toString());

    }

    public static ResponseEntity<ErrorResponse> exceptionHandler(NotFoundException e) {
        ErrorResponse itemErrorResponse = new ErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(itemErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
