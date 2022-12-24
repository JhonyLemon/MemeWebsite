package pl.jhonylemon.memewebsite.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPostDto {
    private Long commentId;
    private String comment;
}
