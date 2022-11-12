package pl.jhonylemon.memewebsite.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.account.AccountGetShortDto;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {
    private Long id;
    private AccountGetShortDto account;
    private Long replyToId;
    private String comment;
    private List<CommentGetDto> childComments;
    private CommentStatisticGetDto commentStatistics;
}
