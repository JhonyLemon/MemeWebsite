package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.PostFileMapper;
import pl.jhonylemon.memewebsite.service.PostFileService;

@RestController
@RequiredArgsConstructor
public class PostFileController {

    private final PostFileService postFileService;
    private final PostFileMapper postFileMapper;

}
