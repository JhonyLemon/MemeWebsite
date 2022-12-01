package pl.jhonylemon.memewebsite.exception.accountrole;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountRoleDefaultRoleException extends BusinessException {
    public AccountRoleDefaultRoleException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountRoleDefaultRoleException() {
        super(HttpStatus.BAD_REQUEST, "Can't delete default role");
    }
}
