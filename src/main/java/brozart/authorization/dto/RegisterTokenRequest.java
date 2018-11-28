package brozart.authorization.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterTokenRequest {

    private String token;
    private String registrationBaseUrl;
}
