package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AccountGetShortDto {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private Long profilePhotoId;
}
