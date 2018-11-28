package brozart.authorization.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomUserDetailsTest {

    @Test
    public void testCreateCustomUserDetails() {
        final User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("pwd");
        user.setEmail("email");
        user.setEnabled(true);

        final CustomUserDetails details = new CustomUserDetails(user);
        assertNull(details.getAuthorities());
        assertEquals(user.getPassword(), details.getPassword());
        assertEquals(user.getUsername(), details.getUsername());
        assertTrue(details.isAccountNonExpired());
        assertTrue(details.isAccountNonLocked());
        assertTrue(details.isCredentialsNonExpired());
        assertTrue(details.isEnabled());
    }
}
