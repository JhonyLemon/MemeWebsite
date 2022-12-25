package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.CommentApi;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.model.CommentGetModelApi;
import pl.jhonylemon.memewebsite.model.CommentPostModelApi;
import pl.jhonylemon.memewebsite.service.comment.CommentService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CommentController implements CommentApi {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @Override
    @PreAuthorize("hasAuthority('USER_DELETE') or hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> deleteComment(Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<CommentGetModelApi> reply(Long id, CommentPostModelApi commentPostModelApi) {
        return ResponseEntity.ok().body(
                commentMapper.commentGetDtoToModelApi(
                        commentService.reply(
                                id,
                                commentMapper.commentPostModelApiToDto(commentPostModelApi)
                        )
                )
        );
    }
}