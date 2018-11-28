package brozart.authorization.exception;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomExceptionHandlerTest {

    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    public void testHandleEmailAlreadyInUse() {
        final EmailAlreadyInUseException ex = new EmailAlreadyInUseException();

        final ResponseEntity<Object> result = customExceptionHandler.handleEmailExists(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }

    @Test
    public void testHandleEmailNotFound() {
        final EmailNotFoundException ex = new EmailNotFoundException("email");

        final ResponseEntity<Object> result = customExceptionHandler.handleEmailNotFound(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }

    @Test
    public void testHandleInvalidRegistrationToken() {
        final InvalidRegistrationTokenException ex = new InvalidRegistrationTokenException();

        final ResponseEntity<Object> result = customExceptionHandler.handleInvalidRegistrationToken(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }

    @Test
    public void testHandleExpiredRegistrationToken() {
        final ExpiredRegistrationTokenException ex = new ExpiredRegistrationTokenException();

        final ResponseEntity<Object> result = customExceptionHandler.handleExpiredRegistrationToken(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }

    @Test
    public void testHandleInvalidResetPasswordToken() {
        final InvalidResetPasswordTokenException ex = new InvalidResetPasswordTokenException();

        final ResponseEntity<Object> result = customExceptionHandler.handleInvalidResetPasswordToken(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }

    @Test
    public void testHandleExpiredResetPasswordToken() {
        final ExpiredResetPasswordTokenException ex = new ExpiredResetPasswordTokenException();

        final ResponseEntity<Object> result = customExceptionHandler.handleExpiredResetPasswordToken(ex);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        final CustomExceptionHandler.ApiError error = (CustomExceptionHandler.ApiError) result.getBody();
        assertNotNull(error);
        assertEquals(ex.getMessage(), error.getMessage());
        assertEquals(400, error.getStatusCode());
        assertEquals("400 BAD_REQUEST", error.getStatus());
    }
}
