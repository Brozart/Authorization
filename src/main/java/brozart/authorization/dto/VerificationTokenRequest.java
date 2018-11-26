package brozart.authorization.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerificationTokenRequest {

    private String token;
    private String verificationBaseUrl;
}
