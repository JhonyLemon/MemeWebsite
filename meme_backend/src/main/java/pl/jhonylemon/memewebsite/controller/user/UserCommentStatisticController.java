package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.service.commentstatistic.user.UserCommentStatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.CommentStatistic.COMMENT_STATISTIC_PATH)
@CrossOrigin
public class UserCommentStatisticController {

    private final UserCommentStatisticService commentStatisticService;
    private final CommentStatisticMapper commentStatisticMapper;

    @PostMapping(path = ApiPaths.CommentStatistic.POST_STATISTIC_SET_VOTE)
    public ResponseEntity<CommentStatisticGetDto> vote(@PathVariable Long userId, @PathVariable Long commentId, @PathVariable Boolean vote){
        return ResponseEntity.ok().body(commentStatisticService.setVoteStatistic(userId,commentId,vote));
    }

}
