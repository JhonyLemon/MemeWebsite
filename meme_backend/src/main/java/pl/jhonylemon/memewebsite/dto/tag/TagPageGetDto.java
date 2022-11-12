package pl.jhonylemon.memewebsite.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagPageGetDto {
    private List<TagGetDto> tags;
    private int totalNumberOfPages;
    private long totalNumberOfElements;
    private TagFilterDto filters;
}
