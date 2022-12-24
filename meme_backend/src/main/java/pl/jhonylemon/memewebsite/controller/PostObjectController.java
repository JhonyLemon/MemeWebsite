package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.api.PostObjectApi;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.model.PostObjectFullGetModelApi;
import pl.jhonylemon.memewebsite.service.postobject.PostObjectService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostObjectController implements PostObjectApi {

    private final PostObjectMapper postObjectMapper;
    private final PostObjectService postObjectService;

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostObjectFullGetModelApi> createPostObject(Long id, Long order, MultipartFile file, String description) {
        return ResponseEntity.ok().body(
                postObjectMapper.postObjectModelApiToFullGetApi(
                        postObjectService.createPostObject(
                                id,
                                order,
                                file,
                                description
                        ))
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_DELETE') or hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> deletePostObject(Long id) {
        postObjectService.deletePostObject(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PostObjectFullGetModelApi> getPostObject(Long id) {
        return ResponseEntity.ok().body(
                postObjectMapper.postObjectModelApiToFullGetApi(
                        postObjectService.getPostObject(
                                id
                        ))
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT') or hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<PostObjectFullGetModelApi> updatePostObject(Long id, MultipartFile file, String description) {
        return ResponseEntity.ok().body(
                postObjectMapper.postObjectModelApiToFullGetApi(
                        postObjectService.updatePostObject(
                                id,
                                file,
                                description
                        ))
        );
    }
}
