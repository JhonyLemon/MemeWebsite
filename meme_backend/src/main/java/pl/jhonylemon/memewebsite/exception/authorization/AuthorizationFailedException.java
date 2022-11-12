package pl.jhonylemon.memewebsite.exception.authorization;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AuthorizationFailedException extends BusinessException {
    public AuthorizationFailedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public AuthorizationFailedException() {
        super(HttpStatus.BAD_REQUEST, "Authorization failed");
    }
}

