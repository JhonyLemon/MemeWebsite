package pl.jhonylemon.memewebsite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DateRangeDto {
    private LocalDate min;
    private LocalDate max;
}
