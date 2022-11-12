package pl.jhonylemon.memewebsite.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    protected final HttpStatus status;
    protected final String message;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
    public BusinessException() {
        super("Unexpected error occurred");
        this.message = "Unexpected error occurred";
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
