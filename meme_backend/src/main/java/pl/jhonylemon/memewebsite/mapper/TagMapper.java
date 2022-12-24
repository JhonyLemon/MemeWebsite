package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagPageGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.model.TagGetModelApi;
import pl.jhonylemon.memewebsite.model.TagPageGetModelApi;
import pl.jhonylemon.memewebsite.model.TagRequestModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            NullableMapper.class
        }
)
public interface TagMapper {

    TagGetDto tagToGetDto(Tag tag);
    TagGetModelApi tagGetDtoToModelApi(TagGetDto tagGetDto);
    TagPageGetModelApi tagPageDtoToModelApi(TagPageGetDto tagPageGetDto);
    TagRequestDto tagRequestModelApiToDto(TagRequestModelApi tagGetDto);




}
