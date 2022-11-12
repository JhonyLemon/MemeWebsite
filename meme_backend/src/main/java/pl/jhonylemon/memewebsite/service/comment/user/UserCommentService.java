package pl.jhonylemon.memewebsite.service.comment.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.AccountGetShortDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.exception.comment.CommentInvalidParamException;
import pl.jhonylemon.memewebsite.exception.comment.CommentNotFoundException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.CommentRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final GuestAccountService guestAccountService;
    private final AccountRepository accountRepository;

    @Transactional
    public CommentGetDto replyTo(Long postId, Long commentId, CommentPostDto commentPostDto){
        if (postId == null || postId<1) {
            throw new CommentInvalidParamException();
        }
        if (commentPostDto == null) {
            throw new CommentInvalidParamException();
        }
        if (commentPostDto.getComment() == null || commentPostDto.getComment().isEmpty()) {
            throw new CommentInvalidParamException();
        }
        if (commentPostDto.getAccountId() == null || commentPostDto.getAccountId()<1) {
            throw new CommentInvalidParamException();
        }

        Comment parentComment = commentRepository.findById(commentId).orElseThrow(()->{
            throw new CommentNotFoundException();
        });

        Post post = postRepository.findById(postId).orElseThrow(()->{
            throw new PostNotFoundException();
        });

        Account account = accountRepository.findById(commentPostDto.getAccountId())
                .orElseThrow(()->{
                    throw new AccountNotFoundException();
                });

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                (String) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
        );

        if (!authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }

        Comment comment= Comment.builder()
                .account(account)
                .post(post)
                .comment(commentPostDto.getComment())
                .build();
        if(commentId!=null){
            comment.setReplyTo(parentComment);
        }

        commentRepository.save(comment);
        return commentMapper.commentToGetDto(comment);
    }
}
