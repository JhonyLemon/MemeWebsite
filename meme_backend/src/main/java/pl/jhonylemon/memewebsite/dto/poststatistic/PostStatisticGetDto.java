package pl.jhonylemon.memewebsite.dto.poststatistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.PostStatistic;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PostStatisticGetDto {
    private Long postId;
    private Long seenCount;
    private Long upVoteCount;
    private Long downVoteCount;
    private Long favoriteCount;

    private Boolean yourVote;
    private Boolean yourFavorite;
    private Boolean yourSeen;

    public void expandData(Optional<PostStatistic> optionalPostStatistic) {

        if (optionalPostStatistic.isPresent()) {
            PostStatistic postStatistic = optionalPostStatistic.get();
            if (postStatistic.getUpVote() == null || postStatistic.getDownVote() == null) {
                this.setYourVote(null);
            }else if (!postStatistic.getUpVote() && !postStatistic.getDownVote()) {
                this.setYourVote(null);
            } else if (postStatistic.getUpVote()) {
                this.setYourVote(true);
            } else if (postStatistic.getDownVote()) {
                this.setYourVote(false);
            }
            if (postStatistic.getFavorite() == null || !postStatistic.getFavorite()) {
                this.setYourFavorite(false);
            } else if (postStatistic.getFavorite()) {
                this.setYourFavorite(true);
            }
            if (postStatistic.getSeen() == null || !postStatistic.getSeen()) {
                this.setYourSeen(false);
            } else if (postStatistic.getSeen()) {
                this.setYourSeen(true);
            }
        }
    }

}
