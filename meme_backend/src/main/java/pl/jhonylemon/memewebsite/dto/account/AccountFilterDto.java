package pl.jhonylemon.memewebsite.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;

@Getter
@Setter
@NoArgsConstructor
public class AccountFilterDto {
    private DateRangeDto creationDate;
    private LongRangeDto comments;
    private LongRangeDto posts;
    private LongRangeDto upVotes;
    private LongRangeDto downVotes;
    private String accountName;
    private Boolean enabled;
    private Boolean banned;
}
