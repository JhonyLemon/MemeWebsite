package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;
import pl.jhonylemon.memewebsite.model.CommentStatisticGetModelApi;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentStatisticMapper {

    default CommentStatisticGetDto commentStatisticGetDto(List<CommentStatistic> commentStatisticList){
        CommentStatisticGetDto commentStatisticGetDto = new CommentStatisticGetDto();

        if(commentStatisticList==null){
            commentStatisticGetDto.setUpVoteCount(0L);
            commentStatisticGetDto.setDownVoteCount(0L);
            return commentStatisticGetDto;
        }

        commentStatisticList.stream()
                .findAny()
                .map(CommentStatistic::getComment)
                .map(Comment::getId)
                .ifPresent(commentStatisticGetDto::setCommentId);
        commentStatisticGetDto.setDownVoteCount(
                commentStatisticList.stream()
                        .map(CommentStatistic::getDownVote)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        commentStatisticGetDto.setUpVoteCount(
                commentStatisticList.stream()
                        .map(CommentStatistic::getUpVote)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        return  commentStatisticGetDto;
    }

    CommentStatisticGetModelApi commentStatisticGetDtoToModelApi(CommentStatisticGetDto commentStatisticGetDto);

}
