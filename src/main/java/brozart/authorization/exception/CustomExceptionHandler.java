package brozart.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmailExistsException.class})
    protected ResponseEntity<Object> handleEmailExists(final EmailExistsException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return toResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> toResponseEntity(final ApiError apiError, final HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
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
