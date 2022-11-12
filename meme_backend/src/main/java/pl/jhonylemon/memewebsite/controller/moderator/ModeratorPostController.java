package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.service.post.moderator.ModeratorPostService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.Post.POST_PATH)
public class ModeratorPostController {

    private final ModeratorPostService postService;
    private final PostMapper postMapper;

    @DeleteMapping(path = ApiPaths.Post.POST_DELETE)
    public ResponseEntity<PostGetFullDto> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Post.POST_UPDATE)
    public ResponseEntity<PostGetFullDto> updatePost(@PathVariable Long id,@RequestBody PostPutDto postPutDto){
        return ResponseEntity.ok().body(postService.updatePost(id,postPutDto));
    }

}
