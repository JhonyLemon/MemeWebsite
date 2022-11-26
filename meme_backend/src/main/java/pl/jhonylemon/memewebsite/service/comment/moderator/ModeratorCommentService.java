package pl.jhonylemon.memewebsite.service.comment.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.exception.comment.CommentInvalidParamException;
import pl.jhonylemon.memewebsite.exception.comment.CommentNotFoundException;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.CommentRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ModeratorCommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final GuestAccountService guestAccountService;
    private final AccountRepository accountRepository;

    @Transactional
    public void deleteComment(Long id){
        if (id == null || id<1) {
            throw new CommentInvalidParamException();
        }
        Comment comment = commentRepository.findById(id).orElseThrow(()->{
            throw new CommentNotFoundException();
        });
        if(comment.getChildComments() == null){
            comment.setComment("");
            comment.setAccount(null);
        }else{
            commentRepository.delete(comment);
        }
    }

}

