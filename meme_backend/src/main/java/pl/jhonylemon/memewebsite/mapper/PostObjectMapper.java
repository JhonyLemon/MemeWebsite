package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.postobject.*;
import pl.jhonylemon.memewebsite.entity.PostObject;
import pl.jhonylemon.memewebsite.model.PostObjectFullGetModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostObjectMapper {

    @Mapping(source = "post.id", target = "postId")
    PostObjectFullGetDto postObjectToFullGetDto(PostObject postObject);

    @Mapping(source = "post.id", target = "postId")
    PostObjectShortGetDto postObjectToShortGetDto(PostObject postObject);

    PostObjectFullGetModelApi postObjectModelApiToFullGetApi(PostObjectFullGetDto postObject);

}
