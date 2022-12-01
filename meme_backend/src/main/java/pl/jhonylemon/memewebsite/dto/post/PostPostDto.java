package pl.jhonylemon.memewebsite.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostDto {
    private String title;
    private List<Long> tags;
    private Boolean visible;

    @JsonIgnore
    public boolean validateRequest(){
        return title!=null &&
                visible !=null;
    }

}
