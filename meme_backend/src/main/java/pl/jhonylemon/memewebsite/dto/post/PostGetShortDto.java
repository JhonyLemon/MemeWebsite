package pl.jhonylemon.memewebsite.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;

@Getter
@Setter
@NoArgsConstructor
public class PostGetShortDto {
    private Long id;
    private String title;
    private Long firstFileId;
    private AccountGetFullDto account;
    private PostStatisticGetDto postStatistics;
}
