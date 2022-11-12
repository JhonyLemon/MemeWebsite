package pl.jhonylemon.memewebsite.exception.poststatistic;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class PostStatisticInvalidParamException extends BusinessException {
    public PostStatisticInvalidParamException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostStatisticInvalidParamException() {
        super(HttpStatus.BAD_REQUEST, "Invalid argument");
    }
}
