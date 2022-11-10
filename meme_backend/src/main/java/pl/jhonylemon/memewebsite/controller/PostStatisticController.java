package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.service.PostStatisticService;

@RestController
@RequiredArgsConstructor
public class PostStatisticController {

    private final PostStatisticService postStatisticService;
    private final PostStatisticMapper postStatisticMapper;

}
