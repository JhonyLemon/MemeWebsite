package pl.jhonylemon.memewebsite.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PostPostDto {
    private List<MultipartFile> files;
    private String title;
    private List<String> descriptions;
    private List<Long> tags;
    private Boolean visible;

    @JsonIgnore
    public boolean validateRequest(){
        return files!=null &&
                descriptions!=null &&
                files.size() == descriptions.size() &&
                title!=null &&
                visible !=null;
    }

}
