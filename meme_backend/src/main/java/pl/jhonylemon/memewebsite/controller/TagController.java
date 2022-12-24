package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.TagApi;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.model.TagGetModelApi;
import pl.jhonylemon.memewebsite.model.TagPageGetModelApi;
import pl.jhonylemon.memewebsite.model.TagRequestModelApi;
import pl.jhonylemon.memewebsite.service.tag.TagService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TagController implements TagApi {

    private final TagService tagService;
    private final TagMapper tagMapper;


    @Override
    @PreAuthorize("hasAuthority('MODERATOR_ADD')")
    public ResponseEntity<TagGetModelApi> addTag(String tag) {
        return ResponseEntity.ok().body(
                tagMapper.tagGetDtoToModelApi(tagService.addTag(tag))
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<TagGetModelApi> changeTag(Long id, String tag) {
        return ResponseEntity.ok().body(
                tagMapper.tagGetDtoToModelApi(tagService.changeTag(id, tag))
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> deleteTag(Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TagPageGetModelApi> getAllTags(TagRequestModelApi tagRequestModelApi) {
        return ResponseEntity.ok().body(
                tagMapper.tagPageDtoToModelApi(
                        tagService.getAllTags(
                                tagMapper.tagRequestModelApiToDto(tagRequestModelApi)
                        )
                )
        );
    }

    @Override
    public ResponseEntity<TagGetModelApi> getTag(Long id) {
        return ResponseEntity.ok().body(
                tagMapper.tagGetDtoToModelApi(tagService.getTag(id))
        );
    }
}
