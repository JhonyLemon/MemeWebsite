package pl.jhonylemon.memewebsite.dto.commentstatistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentStatisticGetDto {
    private Long commentId;
    private Long upVoteCount;
    private Long downVoteCount;
}
