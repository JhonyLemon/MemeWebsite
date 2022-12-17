package pl.jhonylemon.memewebsite.dto.postobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostObjectShortGetDto {
    private Long id;
    private Long postId;
    private String fileName;
    private String mimeType;
    private String description;
}
