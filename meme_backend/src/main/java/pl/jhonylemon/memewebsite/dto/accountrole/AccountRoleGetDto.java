package pl.jhonylemon.memewebsite.dto.accountrole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountRoleGetDto {
    private Long id;
    private String role;
    private List<AccountPermissionGetDto> permissions;
}
