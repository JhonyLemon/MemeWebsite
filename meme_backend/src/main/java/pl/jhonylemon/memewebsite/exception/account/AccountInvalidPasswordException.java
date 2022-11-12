package pl.jhonylemon.memewebsite.exception.account;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountInvalidPasswordException extends BusinessException {
    public AccountInvalidPasswordException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountInvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Invalid password");
    }
}

