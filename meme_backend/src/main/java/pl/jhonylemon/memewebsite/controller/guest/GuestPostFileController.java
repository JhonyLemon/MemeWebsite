package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.postfile.PostFileFullGetDto;
import pl.jhonylemon.memewebsite.dto.postfile.PostFileShortGetDto;
import pl.jhonylemon.memewebsite.mapper.PostFileMapper;
import pl.jhonylemon.memewebsite.service.postfile.guest.GuestPostFileService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.PostFile.POST_PATH)
public class GuestPostFileController {

    private final GuestPostFileService postFileService;
    private final PostFileMapper postFileMapper;

    @GetMapping(path = ApiPaths.PostFile.POST_GET_FULL)
    public ResponseEntity<PostFileFullGetDto> getFullPostFile(@PathVariable Long id){
        return ResponseEntity.ok().body(postFileService.getFullPostFileById(id));
    }

    @GetMapping(path = ApiPaths.PostFile.POST_GET_SHORT)
    public ResponseEntity<PostFileShortGetDto> getShortPostFile(@PathVariable Long id){
        return ResponseEntity.ok().body(postFileService.getShortPostFileById(id));
    }

    @GetMapping(path = ApiPaths.PostFile.POST_GET_FILE)
    public ResponseEntity<byte[]> getPostFileFile(@PathVariable Long id){
        return ResponseEntity.ok().body(postFileService.getPostFileFileById(id));
    }


}
