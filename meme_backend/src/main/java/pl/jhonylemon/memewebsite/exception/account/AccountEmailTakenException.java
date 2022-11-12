package pl.jhonylemon.memewebsite.exception.account;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountEmailTakenException extends BusinessException {
    public AccountEmailTakenException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountEmailTakenException() {
        super(HttpStatus.BAD_REQUEST, "Account with given email address already exists");
    }
}

