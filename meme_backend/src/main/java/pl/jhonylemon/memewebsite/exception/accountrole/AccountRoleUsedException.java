package pl.jhonylemon.memewebsite.exception.accountrole;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountRoleUsedException extends BusinessException {
    public AccountRoleUsedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountRoleUsedException() {
        super(HttpStatus.BAD_REQUEST, "Can't delete used role");
    }
}
