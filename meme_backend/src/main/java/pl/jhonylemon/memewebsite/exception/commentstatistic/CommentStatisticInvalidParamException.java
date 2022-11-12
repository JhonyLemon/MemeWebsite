package pl.jhonylemon.memewebsite.exception.commentstatistic;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class CommentStatisticInvalidParamException extends BusinessException {
    public CommentStatisticInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public CommentStatisticInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
