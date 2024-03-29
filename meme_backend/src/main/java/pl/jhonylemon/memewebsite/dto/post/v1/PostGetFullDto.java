package pl.jhonylemon.memewebsite.dto.post.v1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.postobject.PostObjectShortGetDto;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostGetFullDto {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private Boolean visible;
    private List<PostObjectShortGetDto> postObjects;
    private List<TagGetDto> tags;
    private List<CommentGetDto> comments;
    private AccountGetFullDto account;
    private PostStatisticGetDto postStatistics;
}
