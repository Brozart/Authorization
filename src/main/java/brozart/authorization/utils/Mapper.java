package brozart.authorization.utils;

import brozart.authorization.dto.UserDTO;
import brozart.authorization.user.User;
import org.modelmapper.ModelMapper;

public class Mapper {

    private static final ModelMapper MAPPER = new ModelMapper();

    public static UserDTO map(final User user) {
        return MAPPER.map(user, UserDTO.class);
    }

    public static User map(final UserDTO userDto) {
        return MAPPER.map(userDto, User.class);
    }
}
