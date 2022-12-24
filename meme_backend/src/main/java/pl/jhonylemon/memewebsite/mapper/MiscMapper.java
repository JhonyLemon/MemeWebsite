package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;
import pl.jhonylemon.memewebsite.model.DateRangeModelApi;
import pl.jhonylemon.memewebsite.model.LongRangeModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MiscMapper {


    LongRangeModelApi longRangeDtoToApi(LongRangeDto dto);
    LongRangeDto longRangeApiToDto(LongRangeModelApi modelApi);

    DateRangeModelApi dateRangeDtoToApi(DateRangeDto dto);
    DateRangeDto dateRangeApiToDto(DateRangeModelApi modelApi);



}
