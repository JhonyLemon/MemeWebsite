package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.service.poststatistic.user.UserPostStatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.PostStatistic.POST_STATISTIC_PATH)
@CrossOrigin
public class UserPostStatisticController {

    private final UserPostStatisticService postStatisticService;
    private final PostStatisticMapper postStatisticMapper;

    @PostMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_SET_VOTE)
    public ResponseEntity<PostStatisticGetDto> setVoteStatistic(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Boolean vote){
        return ResponseEntity.ok().body(postStatisticService.setVoteStatistic(userId, postId, vote));
    }
    @PostMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_SET_FAVORITE)
    public ResponseEntity<PostStatisticGetDto> setFavoriteStatistic(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Boolean favorite){
        return ResponseEntity.ok().body(postStatisticService.setFavoriteStatistic(userId, postId, favorite));
    }

}
