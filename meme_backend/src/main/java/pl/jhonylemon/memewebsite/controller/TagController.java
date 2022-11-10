package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.service.TagService;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

}
