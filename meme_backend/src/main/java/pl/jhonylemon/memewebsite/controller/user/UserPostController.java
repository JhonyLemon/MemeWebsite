package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostPostDto;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.service.post.user.UserPostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.Post.POST_PATH)
@CrossOrigin
public class UserPostController {

    private final UserPostService postService;
    private final PostMapper postMapper;

    @PostMapping(path = ApiPaths.Post.POST_CREATE)
    public ResponseEntity<PostGetFullDto> createPost(
            @PathVariable() Long id,
            @RequestPart(value = "files") List<MultipartFile> files,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "descriptions") List<String> descriptions,
            @RequestParam(value = "tags",required = false) List<Long> tags,
            @RequestParam(value = "visible",defaultValue = "true",required = false) Boolean visible
    ) {
        return ResponseEntity.ok().body(postService.createPost(id,new PostPostDto(
                files,title,descriptions,tags,visible
        )));
    }

    @DeleteMapping(path = ApiPaths.Post.POST_DELETE)
    public ResponseEntity<PostGetFullDto> deletePostSelf(@PathVariable Long id){
        postService.deletePostSelf(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Post.POST_UPDATE)
    public ResponseEntity<PostGetFullDto> updatePostSelf(
            @PathVariable Long id,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "descriptions",required = false) List<String> descriptions,
            @RequestParam(value = "tags",required = false) List<Long> tags,
            @RequestParam(value = "visible",required = false) Boolean visible
    ){
        return ResponseEntity.ok().body(postService.updatePostSelf(id,new PostPutDto(
                files,title,descriptions,tags,visible
        )));
    }

}
