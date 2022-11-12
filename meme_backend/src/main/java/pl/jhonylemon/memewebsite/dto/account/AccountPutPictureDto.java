package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountPutPictureDto {
    private Long id;
    private Long profilePictureId;
}
