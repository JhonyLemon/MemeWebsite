package pl.jhonylemon.memewebsite.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.enums.Permissions;
import pl.jhonylemon.memewebsite.exception.authentication.NotEnoughPermissionsException;
import pl.jhonylemon.memewebsite.exception.comment.CommentInvalidParamException;
import pl.jhonylemon.memewebsite.exception.comment.CommentNotFoundException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.CommentRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;
import pl.jhonylemon.memewebsite.util.FunctionHolder;
import pl.jhonylemon.memewebsite.util.Validator;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CustomUserDetailsService userDetailsService;
    private final AccountRepository accountRepository;

    @Transactional
    public void deleteComment(Long id) {
        if (id == null || id < 1) {
            throw new CommentInvalidParamException();
        }
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            throw new CommentNotFoundException();
        });

        Account account = userDetailsService.currentUser();
        List<String> permissions = userDetailsService.currentUserPermissions();

        Map<String, FunctionHolder> map = new LinkedHashMap();
        map.put(Permissions.MODERATOR_DELETE.getName(), () -> {});
        map.put(Permissions.USER_DELETE.getName(), () -> {
            if(!comment.getAccount().getId().equals(account.getId())){
                throw new NotEnoughPermissionsException();
            }
        });
        Validator.checkPermission(permissions, map);

        comment.getChildComments().forEach(c->{
            c.setReplyTo(null);
        });

        if (comment.getChildComments() == null) {
            comment.setComment("");
            comment.setAccount(null);

        } else {
            commentRepository.delete(comment);
        }
    }

    @Transactional
    public CommentGetDto reply(Long postId, CommentPostDto commentPostDto) {
        if (postId == null || postId < 1) {
            throw new CommentInvalidParamException();
        }
        if (commentPostDto == null) {
            throw new CommentInvalidParamException();
        }
        if (commentPostDto.getComment() == null || commentPostDto.getComment().isEmpty()) {
            throw new CommentInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        Comment comment = Comment.builder()
                .account(account)
                .post(post)
                .comment(commentPostDto.getComment())
                .build();
        if (commentPostDto.getCommentId() != null) {
            comment.setReplyTo(commentRepository.findById(commentPostDto.getCommentId())
                    .orElseThrow(() -> {
                        throw new CommentNotFoundException();
                    }));
        }

        commentRepository.save(comment);
        return commentMapper.commentToGetDto(comment);
    }
}
