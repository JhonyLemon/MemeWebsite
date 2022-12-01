package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostGetShortDto;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostObject;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PostObjectMapper.class,
                PostStatisticMapper.class,
                TagMapper.class,
                CommentMapper.class
        },
        imports = {
            PostObject.class,
            java.util.stream.Collectors.class
        }
)
public interface PostMapper {

    PostGetShortDto postToGetShortDto(Post post);

    PostGetFullDto postToGetFullDto(Post post);

}
