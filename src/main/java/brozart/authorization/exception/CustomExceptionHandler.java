package brozart.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmailAlreadyInUseException.class})
    protected ResponseEntity<Object> handleEmailExists(final EmailAlreadyInUseException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({InvalidRegistrationToken.class})
    protected ResponseEntity<Object> handleInvalidRegistrationToken(final InvalidRegistrationToken ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({ExpiredRegistrationToken.class})
    protected ResponseEntity<Object> handleExpiredVerificationToken(final ExpiredRegistrationToken ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({EmailNotFoundException.class})
    protected ResponseEntity<Object> handleEmailNotFound(final EmailNotFoundException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private ResponseEntity<Object> toResponseEntity(final ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private static class ApiError {
        private final String status;
        private final int statusCode;
        private final String message;

        ApiError(final HttpStatus status, final String message) {
            this.status = status.toString();
            this.statusCode = status.value();
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }
    }
}
