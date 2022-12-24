package pl.jhonylemon.memewebsite.exception.authentication;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class NotEnoughPermissionsException extends BusinessException {
    public NotEnoughPermissionsException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public NotEnoughPermissionsException() {
        super(HttpStatus.BAD_REQUEST, "Required permissions not met");
    }
}

