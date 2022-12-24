package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostObject;
import pl.jhonylemon.memewebsite.model.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PostObjectMapper.class,
                PostStatisticMapper.class,
                TagMapper.class,
                CommentMapper.class,
                MiscMapper.class,
                NullableMapper.class
        },
        imports = {
            PostObject.class,
            java.util.stream.Collectors.class
        }
)
public interface PostMapper {

    PostGetShortDto postToGetShortDto(Post post);

    PostGetFullDto postToGetFullDto(Post post);

    PostPostDto postModelApiTo(PostPostModelApi postPostModelApi);
    PostGetFullModelApi postToGetFullModelApi(PostGetFullDto postGetFullDto);
    PostPutDto postModelApiTo(PostPutModelApi postPutModelApi);

    PostPageGetModelApi postToPostPageGetModelApi(PostPageGetDto postPageGetDto);

    PostRequestDto postToPostRequestDto(PostRequestModelApi postRequestModelApi);

}
