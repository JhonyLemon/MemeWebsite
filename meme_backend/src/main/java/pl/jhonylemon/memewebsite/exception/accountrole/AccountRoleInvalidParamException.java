package pl.jhonylemon.memewebsite.exception.accountrole;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountRoleInvalidParamException extends BusinessException {
    public AccountRoleInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountRoleInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
