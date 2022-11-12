package pl.jhonylemon.memewebsite.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountPutPasswordDto {
    private String newPassword;
    private String oldPassword;

    @JsonIgnore
    public boolean isPasswordValid(){
        return newPassword!=null && !newPassword.isBlank() && newPassword.length()>6;
    }
}
