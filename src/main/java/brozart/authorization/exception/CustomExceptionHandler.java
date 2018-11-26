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
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return toResponseEntity(apiError);
    }

    @ExceptionHandler({InvalidVerificationTokenException.class})
    protected ResponseEntity<Object> handleInvalidVerificationToken(final InvalidVerificationTokenException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return toResponseEntity(apiError);
    }

    @ExceptionHandler({ExpiredVerificationTokenException.class})
    protected ResponseEntity<Object> handleExpiredVerificationToken(final ExpiredVerificationTokenException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return toResponseEntity(apiError);
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
