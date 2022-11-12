package pl.jhonylemon.memewebsite.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AccountPostDto {
    private String name;
    private String email;
    private String password;
    private Long profilePictureId;

    @JsonIgnore
    public boolean isNameValid(){
        return name!=null && !name.isBlank();
    }
    @JsonIgnore
    public boolean isEmailValid(){
        return email!=null && !email.isBlank() && validate(email);
    }
    @JsonIgnore
    public boolean isPasswordValid(){
        return password!=null && !password.isBlank() && password.length()>6;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
