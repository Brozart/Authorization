package brozart.authorization.utils;

import brozart.authorization.dto.UserDTO;
import brozart.authorization.user.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapperTest {

    @Test
    public void testMapUserDTO() {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("username");
        userDTO.setPassword("pwd");
        userDTO.setEmail("email");

        final User map = Mapper.map(userDTO);
        assertEquals(userDTO.getId(), map.getId());
        assertEquals(userDTO.getUsername(), map.getUsername());
        assertEquals(userDTO.getPassword(), map.getPassword());
        assertEquals(userDTO.getEmail(), map.getEmail());
    }

    @Test
    public void testMapUser() {
        final User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("pwd");
        user.setEmail("email");

        final UserDTO map = Mapper.map(user);
        assertEquals(user.getId(), map.getId());
        assertEquals(user.getUsername(), map.getUsername());
        assertEquals(user.getPassword(), map.getPassword());
        assertEquals(user.getEmail(), map.getEmail());
    }
}
