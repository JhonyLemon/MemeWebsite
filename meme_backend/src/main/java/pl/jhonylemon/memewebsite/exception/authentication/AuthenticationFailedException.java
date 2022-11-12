package pl.jhonylemon.memewebsite.exception.authentication;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AuthenticationFailedException extends BusinessException {
    public AuthenticationFailedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public AuthenticationFailedException() {
        super(HttpStatus.BAD_REQUEST, "Authentication failed");
    }
}

