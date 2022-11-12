package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.service.poststatistic.user.UserPostStatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.PostStatistic.POST_STATISTIC_PATH)
public class UserPostStatisticController {

    private final UserPostStatisticService postStatisticService;
    private final PostStatisticMapper postStatisticMapper;

    @PostMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_SET_VOTE)
    public ResponseEntity<PostStatisticGetDto> setVoteStatistic(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Boolean vote){
        return ResponseEntity.ok().body(postStatisticService.setVoteStatistic(userId, postId, vote));
    }
    @PostMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_SET_SEEN)
    public ResponseEntity<PostStatisticGetDto> setSeenStatistic(@PathVariable Long userId, @PathVariable Long postId){
        return ResponseEntity.ok().body(postStatisticService.setSeenStatistic(userId, postId));
    }
    @PostMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_SET_FAVORITE)
    public ResponseEntity<PostStatisticGetDto> setFavoriteStatistic(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Boolean favorite){
        return ResponseEntity.ok().body(postStatisticService.setFavoriteStatistic(userId, postId, favorite));
    }

}
