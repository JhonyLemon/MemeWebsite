package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostStatistic;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostStatisticMapper {

    default PostStatisticGetDto postStatisticToGetDto(List<PostStatistic> postStatisticList){
        PostStatisticGetDto postStatisticGetDto = new PostStatisticGetDto();

        if(postStatisticList==null){
            postStatisticGetDto.setUpVoteCount(0L);
            postStatisticGetDto.setSeenCount(0L);
            postStatisticGetDto.setDownVoteCount(0L);
            postStatisticGetDto.setFavoriteCount(0L);
            return postStatisticGetDto;
        }

        postStatisticList.stream()
                .findAny()
                .map(PostStatistic::getPost)
                .map(Post::getId)
                .ifPresent(postStatisticGetDto::setPostId);
        postStatisticGetDto.setSeenCount(
                postStatisticList.stream()
                        .map(PostStatistic::getSeen)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        postStatisticGetDto.setFavoriteCount(
                postStatisticList.stream()
                        .map(PostStatistic::getFavorite)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        postStatisticGetDto.setDownVoteCount(
                postStatisticList.stream()
                        .map(PostStatistic::getDownVote)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        postStatisticGetDto.setUpVoteCount(
                postStatisticList.stream()
                        .map(PostStatistic::getUpVote)
                        .filter(Objects::nonNull)
                        .filter(s->s)
                        .count()
        );
        return postStatisticGetDto;
    }

}
