package pl.jhonylemon.memewebsite.exception.accountrole;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountRoleNotFoundException extends BusinessException {
    public AccountRoleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public AccountRoleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Role not found");
    }
}

