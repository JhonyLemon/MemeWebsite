package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostGetShortDto;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostFile;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PostFileMapper.class,
                PostStatisticMapper.class,
                TagMapper.class,
                CommentMapper.class
        },
        imports = {
            PostFile.class,
            java.util.stream.Collectors.class
        }
)
public interface PostMapper {

    @Mapping(target = "firstFileId", expression = "java(post.getFiles().get(0).getId())")
    PostGetShortDto postToGetShortDto(Post post);

    @Mapping(target = "filesId", expression ="java(post.getFiles().stream().map(PostFile::getId).collect(Collectors.toList()))")
    PostGetFullDto postToGetFullDto(Post post);

}
