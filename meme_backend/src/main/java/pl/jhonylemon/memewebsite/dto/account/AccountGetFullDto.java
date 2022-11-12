package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountGetFullDto {
    private Long id;
    private String name;
    private String email;
    private LocalDate creationDate;
    private Long profilePhotoId;
    private List<AccountPermissionGetDto> permissions;
    private Boolean enabled;
    private Boolean banned;
}
