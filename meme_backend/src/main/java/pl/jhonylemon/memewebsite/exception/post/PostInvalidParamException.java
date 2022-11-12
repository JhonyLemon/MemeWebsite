package pl.jhonylemon.memewebsite.exception.post;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostInvalidParamException extends BusinessException {
    public PostInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
