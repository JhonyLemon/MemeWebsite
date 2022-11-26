package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.commentstatistic.CommentStatisticGetDto;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.service.commentstatistic.guest.GuestCommentStatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.CommentStatistic.COMMENT_STATISTIC_PATH)
@CrossOrigin
public class GuestCommentStatisticController {

    private final GuestCommentStatisticService commentStatisticService;
    private final CommentStatisticMapper commentStatisticMapper;

    @GetMapping(path = ApiPaths.CommentStatistic.POST_STATISTIC_GET)
    public ResponseEntity<CommentStatisticGetDto> getStatistic(@PathVariable Long id){
        return ResponseEntity.ok().body(commentStatisticService.getStatistic(id));
    }

}
