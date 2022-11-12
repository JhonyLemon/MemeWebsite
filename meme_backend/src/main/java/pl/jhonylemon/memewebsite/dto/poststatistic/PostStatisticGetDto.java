package pl.jhonylemon.memewebsite.dto.poststatistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostStatisticGetDto {
    private Long postId;
    private Long seenCount;
    private Long upVoteCount;
    private Long downVoteCount;
    private Long favoriteCount;
}
