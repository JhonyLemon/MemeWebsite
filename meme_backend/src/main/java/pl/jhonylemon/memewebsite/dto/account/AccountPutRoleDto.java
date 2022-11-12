package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountPutRoleDto {
    private Long id;
    private Long roleId;
}
