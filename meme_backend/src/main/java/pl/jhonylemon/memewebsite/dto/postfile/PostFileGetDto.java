package pl.jhonylemon.memewebsite.dto.postfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFileGetDto {
    private Long id;
    private Long postId;
    private String fileName;
    private byte[] file;
    private String mimeType;
    private String description;
}
