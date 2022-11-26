package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.service.comment.user.UserCommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.Comment.COMMENT_PATH)
@CrossOrigin
public class UserCommentController {

    private final UserCommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping(path = ApiPaths.Comment.COMMENT_REPLY)
    public ResponseEntity<CommentGetDto> reply(@PathVariable Long postId,@PathVariable Long commentId, @RequestBody CommentPostDto commentPostDto){
        return ResponseEntity.ok().body(commentService.replyTo(postId,commentId,commentPostDto));
    }

}
