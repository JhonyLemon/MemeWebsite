package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.service.postobject.user.UserPostObjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.PostObject.POST_OBJECT_PATH)
@CrossOrigin
public class UserPostObjectController {

    private final UserPostObjectService postObjectService;
    private final PostMapper postMapper;

    @PostMapping(
            path = ApiPaths.PostObject.POST_OBJECT_CREATE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostGetFullDto> createPostObject(
            @PathVariable() Long id,
            @PathVariable Long order,
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "description",required = false) String description
    ) {
        return ResponseEntity.ok().body(postObjectService.createPostObject(id,order,file,description));
    }


    @DeleteMapping(path = ApiPaths.PostObject.POST_OBJECT_DELETE)
    public ResponseEntity<Void> deletePostObjectSelf(@PathVariable Long id){
        postObjectService.deletePostObjectSelf(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(
            path = ApiPaths.PostObject.POST_OBJECT_UPDATE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostGetFullDto> updatePostObject(
            @PathVariable Long id,
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "description") String description
    ){
        return ResponseEntity.ok().body(postObjectService.updatePostObjectSelf(id,file,description));
    }

}
