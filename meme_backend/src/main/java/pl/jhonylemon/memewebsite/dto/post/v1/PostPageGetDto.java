package pl.jhonylemon.memewebsite.dto.post.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.post.PostFilterDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPageGetDto {
    private List<PostGetShortDto> posts;
    private int totalNumberOfPages;
    private long totalNumberOfElements;
    private PostFilterDto filters;
}
