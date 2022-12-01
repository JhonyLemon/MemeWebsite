package pl.jhonylemon.memewebsite.dto.postobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostObjectFullGetDto {
    private Long id;
    private Long postId;
    private String fileName;
    private byte[] content;
    private String mimeType;
    private String charset;
}
