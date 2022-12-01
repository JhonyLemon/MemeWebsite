package pl.jhonylemon.memewebsite.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPutDto {
    private String title;
    private List<Long> tags;

    private Boolean visible;
    private Map<Long,Long> order;

    public boolean isTittleValid(){
        return title!=null && !title.isEmpty();
    }

    public boolean isTagsValid(){
        return tags!=null;
    }

    public boolean isVisibleValid(){
        return visible!=null;
    }

    public boolean isOrderValid(){
        return order!=null && !order.isEmpty();
    }



}
