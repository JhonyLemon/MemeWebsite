package pl.jhonylemon.memewebsite.service.commentstatistic.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;
import pl.jhonylemon.memewebsite.exception.comment.CommentInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.repository.CommentStatisticRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestCommentStatisticService {

    private final CommentStatisticMapper commentStatisticMapper;
    private final CommentStatisticRepository commentStatisticRepository;

    public CommentStatisticGetDto getStatistic(Long id){
        if(id==null || id<1){
            throw new CommentInvalidParamException();
        }
        List<CommentStatistic> statisticList = commentStatisticRepository.findByComment_Id(id);
        return commentStatisticMapper.commentStatisticGetDto(statisticList);
    }

}