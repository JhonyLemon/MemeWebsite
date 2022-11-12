package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostGetShortDto;
import pl.jhonylemon.memewebsite.entity.Post;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PostFileMapper.class,
                PostStatisticMapper.class,
                TagMapper.class,
                CommentMapper.class
        }
)
public interface PostMapper {

    @Mapping(target = "file", expression = "java(new PostFileGetDto(post.getFiles().get(0).getId(),post.getId(),post.getFiles().get(0).getFileName(),post.getFiles().get(0).getFile(),post.getFiles().get(0).getMimeType(),post.getFiles().get(0).getDescription()))")
    PostGetShortDto postToGetShortDto(Post post);

    PostGetFullDto postToGetFullDto(Post post);

}
