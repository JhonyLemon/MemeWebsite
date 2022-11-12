package pl.jhonylemon.memewebsite.exception.profilepicture;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class ProfilePictureInvalidParamException extends BusinessException {
    public ProfilePictureInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public ProfilePictureInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid Param");
    }
}

