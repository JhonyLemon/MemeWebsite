package pl.jhonylemon.memewebsite.exception.commentstatistic;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class CommentStatisticNotFoundException extends BusinessException {
    public CommentStatisticNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public CommentStatisticNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Comment statistic not found");
    }
}
