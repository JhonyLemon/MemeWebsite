package pl.jhonylemon.memewebsite.dto.postfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFileShortGetDto {
    private Long id;
    private Long postId;
    private String fileName;
    private String mimeType;
    private String description;
}
