package pl.jhonylemon.memewebsite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LongRangeDto {
    private Long min;
    private Long max;
}
