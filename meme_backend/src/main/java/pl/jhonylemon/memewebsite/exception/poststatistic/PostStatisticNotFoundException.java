package pl.jhonylemon.memewebsite.exception.poststatistic;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostStatisticNotFoundException extends BusinessException {
    public PostStatisticNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostStatisticNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Post statistic not found");
    }
}
