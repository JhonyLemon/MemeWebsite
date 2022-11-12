package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.service.comment.moderator.ModeratorCommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.Comment.COMMENT_PATH)
public class ModeratorCommentController {

    private ModeratorCommentService commentService;
    private CommentMapper commentMapper;

    @DeleteMapping(path = ApiPaths.Comment.COMMENT_REMOVE)
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
