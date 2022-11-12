package pl.jhonylemon.memewebsite.exception.profilepicture;

import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.exception.BusinessException;

public class ProfilePictureNotFoundException extends BusinessException {
    public ProfilePictureNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public ProfilePictureNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Profile picture not found");
    }
}

