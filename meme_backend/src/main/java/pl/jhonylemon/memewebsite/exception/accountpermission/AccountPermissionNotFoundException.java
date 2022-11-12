package pl.jhonylemon.memewebsite.exception.accountpermission;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class AccountPermissionNotFoundException extends BusinessException {
    public AccountPermissionNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public AccountPermissionNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Permission not found");
    }
}

