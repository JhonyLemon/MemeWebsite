package pl.jhonylemon.memewebsite.exception.account;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Account not found");
    }
}

