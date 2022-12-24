package pl.jhonylemon.memewebsite.exception.postobject;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostObjectNotFoundException extends BusinessException {
    public PostObjectNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostObjectNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Post object not found");
    }
}
