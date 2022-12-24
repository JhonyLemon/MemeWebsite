package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.model.CommentGetModelApi;
import pl.jhonylemon.memewebsite.model.CommentPostModelApi;

import java.util.Optional;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                CommentStatisticMapper.class,
                AccountMapper.class
        },
        imports = {
                Optional.class
        }
)
public interface CommentMapper {

   @Mapping(target = "replyToId",expression = "java(Optional.ofNullable(comment.getReplyTo()).map(Comment::getId).orElse(null))")
   CommentGetDto commentToGetDto(Comment comment);

    CommentGetModelApi commentGetDtoToModelApi(CommentGetDto commentGetDto);
    CommentPostDto commentPostModelApiToDto(CommentPostModelApi commentPostModelApi);

}
