package pl.jhonylemon.memewebsite.dto.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRefreshRequest {
    private String refreshToken;
}
