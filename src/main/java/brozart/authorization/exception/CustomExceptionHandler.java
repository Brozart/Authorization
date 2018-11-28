package brozart.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings({"unused", "WeakerAccess"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Email exceptions
     **/
    @ExceptionHandler({EmailAlreadyInUseException.class})
    protected ResponseEntity<Object> handleEmailExists(final EmailAlreadyInUseException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({EmailNotFoundException.class})
    protected ResponseEntity<Object> handleEmailNotFound(final EmailNotFoundException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Registration exceptions
     **/
    @ExceptionHandler({InvalidRegistrationTokenException.class})
    protected ResponseEntity<Object> handleInvalidRegistrationToken(final InvalidRegistrationTokenException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({ExpiredRegistrationTokenException.class})
    protected ResponseEntity<Object> handleExpiredRegistrationToken(final ExpiredRegistrationTokenException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Reset password exceptions
     **/
    @ExceptionHandler({InvalidResetPasswordTokenException.class})
    protected ResponseEntity<Object> handleInvalidResetPasswordToken(final InvalidResetPasswordTokenException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({ExpiredResetPasswordTokenException.class})
    protected ResponseEntity<Object> handleExpiredResetPasswordToken(final ExpiredResetPasswordTokenException ex) {
        return toResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * General helper method
     */

    private ResponseEntity<Object> toResponseEntity(final ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    protected static class ApiError {
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
