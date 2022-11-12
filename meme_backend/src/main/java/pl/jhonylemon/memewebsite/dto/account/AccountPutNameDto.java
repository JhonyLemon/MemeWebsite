package pl.jhonylemon.memewebsite.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountPutNameDto {
    private Long id;
    private String name;

    @JsonIgnore
    public boolean isNameValid(){
        return name!=null && !name.isBlank();
    }
}
