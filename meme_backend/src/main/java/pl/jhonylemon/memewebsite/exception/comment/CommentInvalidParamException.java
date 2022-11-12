package pl.jhonylemon.memewebsite.exception.comment;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class CommentInvalidParamException extends BusinessException {
    public CommentInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public CommentInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
