package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.post.PostPageGetDto;
import pl.jhonylemon.memewebsite.dto.post.PostRequestDto;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.service.post.guest.GuestPostService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.Post.POST_PATH)
public class GuestPostController {

    private final GuestPostService postService;
    private final PostMapper postMapper;

    @PostMapping(path = ApiPaths.Post.POST_GET_ALL_PAGINATED)
    public ResponseEntity<PostPageGetDto> getAllPosts(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok().body(postService.getAllPosts(postRequestDto));
    }
    @GetMapping(path = ApiPaths.Post.POST_GET)
    public ResponseEntity<PostGetFullDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok().body(postService.getPost(id));
    }

}
