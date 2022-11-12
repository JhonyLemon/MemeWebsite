package pl.jhonylemon.memewebsite.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
