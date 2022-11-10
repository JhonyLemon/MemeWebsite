package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.CommentStatisticMapper;
import pl.jhonylemon.memewebsite.service.CommentStatisticService;

@RestController
@RequiredArgsConstructor
public class CommentStatisticController {

    private final CommentStatisticService commentStatisticService;
    private final CommentStatisticMapper commentStatisticMapper;

}
