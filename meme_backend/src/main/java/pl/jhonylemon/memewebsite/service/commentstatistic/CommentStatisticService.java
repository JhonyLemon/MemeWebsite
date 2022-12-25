package pl.jhonylemon.memewebsite.service.commentstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;
import pl.jhonylemon.memewebsite.exception.authentication.NotEnoughPermissionsException;
import pl.jhonylemon.memewebsite.exception.comment.CommentInvalidParamException;
import pl.jhonylemon.memewebsite.exception.comment.CommentNotFoundException;
import pl.jhonylemon.memewebsite.exception.poststatistic.PostStatisticInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.repository.CommentRepository;
import pl.jhonylemon.memewebsite.repository.CommentStatisticRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentStatisticService {

    private final CommentStatisticMapper commentStatisticMapper;
    private final CommentStatisticRepository commentStatisticRepository;
    private final CommentRepository commentRepository;

    private final CustomUserDetailsService userDetailsService;

    public CommentStatisticGetDto getCommentStatistic(Long id) {
        if(id==null || id<1){
            throw new CommentInvalidParamException();
        }
        List<CommentStatistic> statisticList = commentStatisticRepository.findByComment_Id(id);
        return commentStatisticMapper.commentStatisticGetDto(statisticList);
    }

    @Transactional
    public CommentStatisticGetDto vote(Long commentId, Boolean vote) {
        if(commentId==null || commentId<1){
            throw new PostStatisticInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        if(account==null){
            throw new NotEnoughPermissionsException();
        }

        Optional<CommentStatistic> postStatistic = commentStatisticRepository.findByComment_IdAndAccount_Id(commentId,account.getId());

        if(postStatistic.isPresent()) {
            postStatistic.get().setUpVote(vote != null && (vote));
            postStatistic.get().setDownVote(vote != null && (!vote));
        }else {
            Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
                throw new CommentNotFoundException();
            });
            CommentStatistic postStat = CommentStatistic.builder()
                    .comment(comment)
                    .account(account)
                    .upVote(vote != null && (vote))
                    .downVote(vote != null && (!vote))
                    .build();
            commentStatisticRepository.save(postStat);
        }
        List<CommentStatistic> statisticList = commentStatisticRepository.findByComment_Id(commentId);
        return commentStatisticMapper.commentStatisticGetDto(statisticList);
    }
}
