package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AccountGetFullDto {
    private Long id;
    private String name;
    private String email;
    private LocalDate creationDate;
    private Long profilePhotoId;
    private AccountRoleGetDto role;
    private Boolean enabled;
    private Boolean banned;
}
