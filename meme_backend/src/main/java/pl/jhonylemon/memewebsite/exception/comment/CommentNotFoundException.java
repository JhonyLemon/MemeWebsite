package pl.jhonylemon.memewebsite.exception.comment;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public CommentNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Comment not found");
    }
}
