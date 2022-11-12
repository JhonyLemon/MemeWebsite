package pl.jhonylemon.memewebsite.exception.post;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Post not found");
    }
}
