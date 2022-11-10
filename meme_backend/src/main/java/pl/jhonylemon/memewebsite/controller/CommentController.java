package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;
    private CommentMapper commentMapper;

}
