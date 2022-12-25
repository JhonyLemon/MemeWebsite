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

    pl.jhonylemon.memewebsite.dto.post.v1.PostGetShortDto postToV1GetShortDto(Post post);
    pl.jhonylemon.memewebsite.dto.post.v2.PostGetShortDto postToV2GetShortDto(Post post);

    pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postToV1GetFullDto(Post post);
    pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto postToV2GetFullDto(Post post);

    PostPostDto postModelApiTo(PostPostModelApi postPostModelApi);
    PostGetFullV1ModelApi postToV1GetFullModelApi(pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto);
    PostGetFullV2ModelApi postToV2GetFullModelApi(pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto postGetFullDto);

    PostPutDto postModelApiTo(PostPutModelApi postPutModelApi);

    PostPageGetV1ModelApi postToV1PostPageGetModelApi(pl.jhonylemon.memewebsite.dto.post.v1.PostPageGetDto postPageGetDto);
    PostPageGetV2ModelApi postToV2PostPageGetModelApi(pl.jhonylemon.memewebsite.dto.post.v2.PostPageGetDto postPageGetDto);

    PostRequestDto postToPostRequestDto(PostRequestModelApi postRequestModelApi);

}
