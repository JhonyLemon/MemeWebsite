package pl.jhonylemon.memewebsite.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPutDto {
    private List<MultipartFile> files;
    private String title;
    private List<String> descriptions;
    private List<Long> tags;
    private Boolean visible;
}
