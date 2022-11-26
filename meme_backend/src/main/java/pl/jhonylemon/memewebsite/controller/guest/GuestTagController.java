package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagPageGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.service.tag.guest.GuestTagService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.Tag.TAG_PATH)
@CrossOrigin
public class GuestTagController {

    private final GuestTagService tagService;
    private final TagMapper tagMapper;

    @PostMapping(path = ApiPaths.Tag.TAG_GET_ALL_PAGINATED)
    public ResponseEntity<TagPageGetDto> getAllTags(@RequestBody TagRequestDto tagRequestDto){
        return ResponseEntity.ok().body(tagService.getAllTags(tagRequestDto));
    }
    @GetMapping(path = ApiPaths.Tag.TAG_GET)
    public ResponseEntity<TagGetDto> getTag(@PathVariable Long id){
        return ResponseEntity.ok().body(tagService.getTag(id));
    }

}
