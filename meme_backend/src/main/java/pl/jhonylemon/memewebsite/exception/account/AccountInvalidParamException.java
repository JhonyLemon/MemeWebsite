package pl.jhonylemon.memewebsite.exception.account;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountInvalidParamException extends BusinessException {
    public AccountInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}

