package pl.jhonylemon.memewebsite.dto.accountrole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountRolePostDto {
    private List<Long> permissions;
    private String role;
}
