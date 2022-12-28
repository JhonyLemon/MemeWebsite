package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.PostStatisticApi;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.model.PostStatisticGetModelApi;
import pl.jhonylemon.memewebsite.model.StatisticPutModelApi;
import pl.jhonylemon.memewebsite.service.poststatistic.PostStatisticService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostStatisticController implements PostStatisticApi {

    private final PostStatisticMapper postStatisticMapper;
    private final PostStatisticService postStatisticService;

    @Override
    public ResponseEntity<PostStatisticGetModelApi> getPostStatistic(Long id) {
        return ResponseEntity.ok().body(
                postStatisticMapper.postStatisticsGetDtoToModelApi(
                        postStatisticService.getPostStatistic(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<PostStatisticGetModelApi> setFavoriteStatistic(Long postId, StatisticPutModelApi favorite) {
        return ResponseEntity.ok().body(
                postStatisticMapper.postStatisticsGetDtoToModelApi(
                        postStatisticService.setFavoriteStatistic(postId,favorite.getVote())
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<PostStatisticGetModelApi> setVoteStatistic(Long postId, StatisticPutModelApi vote) {
        return ResponseEntity.ok().body(
                postStatisticMapper.postStatisticsGetDtoToModelApi(
                        postStatisticService.setVoteStatistic(postId,vote.getVote())
                )
        );
    }
}
