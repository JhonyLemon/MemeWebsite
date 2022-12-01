package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostPostDto;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.service.post.user.UserPostService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.Post.POST_PATH)
@CrossOrigin
public class UserPostController {

    private final UserPostService postService;
    private final PostMapper postMapper;

    @PostMapping(path = ApiPaths.Post.POST_CREATE)
    public ResponseEntity<PostGetFullDto> createPost(
            @RequestBody PostPostDto postPostDto
    ) {
        return ResponseEntity.ok().body(postService.createPost(postPostDto));
    }

    @DeleteMapping(path = ApiPaths.Post.POST_DELETE)
    public ResponseEntity<PostGetFullDto> deletePostSelf(@PathVariable Long id){
        postService.deletePostSelf(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Post.POST_UPDATE)
    public ResponseEntity<PostGetFullDto> updatePostSelf(
            @PathVariable Long id,
            @RequestBody PostPutDto postPutDto
    ){
        return ResponseEntity.ok().body(postService.updatePostSelf(id,postPutDto));
    }

    @PostMapping(path = ApiPaths.Post.POST_PUBLISH)
    public ResponseEntity<PostGetFullDto> updatePostSelf(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().body(postService.publishPost(id));
    }

}
