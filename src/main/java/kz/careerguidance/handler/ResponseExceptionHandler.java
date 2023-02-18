package kz.careerguidance.handler;

import kz.careerguidance.util.ErrorResponse;
import kz.careerguidance.util.exceptions.NotFoundException;
import kz.careerguidance.util.exceptions.NotUniqueDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static kz.careerguidance.util.ErrorsUtil.exceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            NotFoundException ex) {
        return exceptionHandler(ex);
    }
}
