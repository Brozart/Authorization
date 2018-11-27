package brozart.authorization.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetTokenRequest {

    private String email;
    private String passwordResetUrl;
}
