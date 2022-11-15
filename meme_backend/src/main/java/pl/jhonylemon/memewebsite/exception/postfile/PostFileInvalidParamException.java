package pl.jhonylemon.memewebsite.exception.postfile;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostFileInvalidParamException extends BusinessException {
    public PostFileInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostFileInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
