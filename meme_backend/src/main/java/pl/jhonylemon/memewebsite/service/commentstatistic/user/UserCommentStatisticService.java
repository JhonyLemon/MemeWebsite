package pl.jhonylemon.memewebsite.service.commentstatistic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.comment.CommentNotFoundException;
import pl.jhonylemon.memewebsite.exception.poststatistic.PostStatisticInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.CommentRepository;
import pl.jhonylemon.memewebsite.repository.CommentStatisticRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommentStatisticService {

    private final CommentStatisticMapper commentStatisticMapper;
    private final CommentStatisticRepository commentStatisticRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentStatisticGetDto setVoteStatistic(Long userId,Long commentId,Boolean vote){
        if(userId==null || userId<1){
            throw new PostStatisticInvalidParamException();
        }
        if(commentId==null || commentId<1){
            throw new PostStatisticInvalidParamException();
        }
        if(vote==null){
            throw new PostStatisticInvalidParamException();
        }
        Optional<CommentStatistic> postStatistic = commentStatisticRepository.findByComment_IdAndAccount_Id(commentId,userId);

        if(postStatistic.isPresent()) {
            postStatistic.get().setVote(vote);
        }else {
            Account account = accountRepository.findById(userId).orElseThrow(()->{
                throw new AccountNotFoundException();
            });

            Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
                throw new CommentNotFoundException();
            });
            CommentStatistic postStat = CommentStatistic.builder()
                    .comment(comment)
                    .account(account)
                    .vote(vote)
                    .build();
            commentStatisticRepository.save(postStat);
        }
        List<CommentStatistic> statisticList = commentStatisticRepository.findByComment_Id(commentId);
        return commentStatisticMapper.commentStatisticGetDto(statisticList);
    }

}