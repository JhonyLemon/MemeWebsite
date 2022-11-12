package pl.jhonylemon.memewebsite.dto.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagGetDto {
    private Long id;
    private String tag;
}
