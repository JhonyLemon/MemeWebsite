package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.postobject.PostObjectFullGetDto;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.service.postobject.guest.GuestObjectFileService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ ApiPaths.PostObject.POST_OBJECT_PATH)
@CrossOrigin
public class GuestPostObjectController {

    private final GuestObjectFileService postFileService;
    private final PostObjectMapper postObjectMapper;

    @GetMapping(path = ApiPaths.PostObject.POST_OBJECT_GET_FILE)
    public ResponseEntity<byte[]> getPostObjectFile(@PathVariable Long id){
        PostObjectFullGetDto postFileDto = postFileService.getFullPostFileById(id);

        MediaType type = MediaType.parseMediaType(postFileDto.getMimeType());
        String contentDisposition = "attachment; filename=\"" + postFileDto.getFileName() + "\"";

        return ResponseEntity.ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(postFileDto.getContent());
    }

    @GetMapping(path = ApiPaths.PostObject.POST_OBJECT_GET)
    public ResponseEntity<PostObjectFullGetDto> getPostObject(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(postFileService.getFullPostFileById(id));
    }

}
