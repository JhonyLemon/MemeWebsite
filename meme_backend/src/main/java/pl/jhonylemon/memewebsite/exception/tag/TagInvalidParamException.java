package pl.jhonylemon.memewebsite.exception.tag;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class TagInvalidParamException extends BusinessException {
    public TagInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public TagInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}

