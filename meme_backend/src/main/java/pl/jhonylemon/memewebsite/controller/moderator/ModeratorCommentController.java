package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.service.comment.moderator.ModeratorCommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.Comment.COMMENT_PATH)
@CrossOrigin
public class ModeratorCommentController {

    private final ModeratorCommentService commentService;
    private final CommentMapper commentMapper;

    @DeleteMapping(path = ApiPaths.Comment.COMMENT_REMOVE)
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
