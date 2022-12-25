package pl.jhonylemon.memewebsite.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostFilterDto {
    private DateRangeDto creationDate;
    private LongRangeDto comments;
    private LongRangeDto upVotes;
    private LongRangeDto downVotes;
    private LongRangeDto favorites;
    private List<Long> tags;
    private String title;
    private Boolean showYourFavorite;
}
