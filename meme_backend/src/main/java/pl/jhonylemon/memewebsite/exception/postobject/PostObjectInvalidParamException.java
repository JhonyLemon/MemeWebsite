package pl.jhonylemon.memewebsite.exception.postobject;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostObjectInvalidParamException extends BusinessException {
    public PostObjectInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostObjectInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
