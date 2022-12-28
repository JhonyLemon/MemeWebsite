package pl.jhonylemon.memewebsite.dto.commentstatistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class CommentStatisticGetDto {
    private Long commentId;
    private Long upVoteCount;
    private Long downVoteCount;

    private Boolean yourVote;

    public void expandData(Optional<CommentStatistic> optionalCommentStatistic) {
        if (optionalCommentStatistic.isPresent()) {
            CommentStatistic commentStatistic = optionalCommentStatistic.get();
            if (commentStatistic.getUpVote() == null || commentStatistic.getDownVote() == null) {
                this.setYourVote(null);
            }else if (!commentStatistic.getUpVote() && !commentStatistic.getDownVote()) {
                this.setYourVote(null);
            }else if (commentStatistic.getDownVote()) {
                this.setYourVote(false);
            } else if (commentStatistic.getUpVote()) {
                this.setYourVote(true);
            }
        }
    }

}
