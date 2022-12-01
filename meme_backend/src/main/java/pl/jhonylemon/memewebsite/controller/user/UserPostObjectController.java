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

import javax.servlet.ServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.PostObject.POST_OBJECT_PATH)
@CrossOrigin
public class UserPostObjectController {

    private final UserPostObjectService postObjectService;
    private final PostMapper postMapper;

    @PostMapping(
            path = ApiPaths.PostObject.POST_OBJECT_CREATE_FILE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostGetFullDto> createPostObjectFile(
            @PathVariable() Long id,
            @PathVariable Long order,
            @RequestPart(value = "file") MultipartFile file
    ) {
        return ResponseEntity.ok().body(postObjectService.createPostObject(id,order,file));
    }

    @PostMapping(path = ApiPaths.PostObject.POST_OBJECT_CREATE_TEXT)
    public ResponseEntity<PostGetFullDto> createPostObjectText(
            @PathVariable() Long id,
            @PathVariable Long order,
            @RequestBody String content,
            ServletRequest request
    ) {
        return ResponseEntity.ok().body(postObjectService
                .createPostObject(id,order,content,request));
    }

    @DeleteMapping(path = ApiPaths.PostObject.POST_OBJECT_DELETE)
    public ResponseEntity<Void> deletePostObjectSelf(@PathVariable Long id){
        postObjectService.deletePostObjectSelf(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(
            path = ApiPaths.PostObject.POST_OBJECT_UPDATE_FILE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostGetFullDto> updatePostObjectFileSelf(
            @PathVariable Long id,
            @RequestPart(value = "file") MultipartFile file
    ){
        return ResponseEntity.ok().body(postObjectService.updatePostObjectSelf(id,file));
    }
    @PutMapping(path = ApiPaths.PostObject.POST_OBJECT_UPDATE_TEXT)
    public ResponseEntity<PostGetFullDto> updatePostObjectTextSelf(
            @PathVariable Long id,
            @RequestBody String content,
            ServletRequest request
    ){
        return ResponseEntity.ok().body(postObjectService
                .updatePostObject(id,content,request));
    }

}
