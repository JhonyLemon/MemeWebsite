package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.CommentStatisticApi;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.model.CommentStatisticGetModelApi;
import pl.jhonylemon.memewebsite.model.StatisticPutModelApi;
import pl.jhonylemon.memewebsite.service.commentstatistic.CommentStatisticService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CommentStatisticController implements CommentStatisticApi {

    private final CommentStatisticService commentStatisticService;
    private final CommentStatisticMapper commentStatisticMapper;

    @Override
    public ResponseEntity<CommentStatisticGetModelApi> getCommentStatistic(Long id) {
        return ResponseEntity.ok().body(
                commentStatisticMapper.commentStatisticGetDtoToModelApi(
                        commentStatisticService.getCommentStatistic(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<CommentStatisticGetModelApi> vote(Long id, StatisticPutModelApi vote) {
        return ResponseEntity.ok().body(
                commentStatisticMapper.commentStatisticGetDtoToModelApi(
                        commentStatisticService.vote(id,vote.getVote())
                )
        );
    }
}
