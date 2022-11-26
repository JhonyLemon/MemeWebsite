package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.service.tag.moderator.ModeratorTagService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.Tag.TAG_PATH)
@CrossOrigin
public class ModeratorTagController {

    private final ModeratorTagService tagService;
    private final TagMapper tagMapper;

    @DeleteMapping(path = ApiPaths.Tag.TAG_REMOVE)
    public ResponseEntity<Void> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Tag.TAG_UPDATE)
    public ResponseEntity<TagGetDto> changeTag(@PathVariable Long id, @PathVariable String tag){
        return ResponseEntity.ok().body(tagService.changeTag(id,tag));
    }
    @PostMapping(path = ApiPaths.Tag.TAG_POST)
    public ResponseEntity<TagGetDto> addTag(@PathVariable String tag){
        return ResponseEntity.ok().body(tagService.addTag(tag));
    }

}
