package pl.jhonylemon.memewebsite.dto.accountpermission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountPermissionPutDto {
    private Long oldPermissionId;
    private Long newPermissionId;
}
