package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.postfile.*;
import pl.jhonylemon.memewebsite.entity.PostFile;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostFileMapper {

    PostFileFullGetDto postFileToFullGetDto(PostFile postFile);

    PostFileShortGetDto postFileToShortGetDto(PostFile postFile);
}
