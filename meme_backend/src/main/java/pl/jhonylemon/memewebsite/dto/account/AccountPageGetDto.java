package pl.jhonylemon.memewebsite.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPageGetDto {
    private List<AccountGetFullDto> accounts;
    private int totalNumberOfPages;
    private long totalNumberOfElements;
    private AccountFilterDto filters;
}
