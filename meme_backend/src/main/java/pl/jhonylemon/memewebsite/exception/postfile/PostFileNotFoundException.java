package pl.jhonylemon.memewebsite.exception.postfile;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostFileNotFoundException extends BusinessException {
    public PostFileNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostFileNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Post file not found");
    }
}
