package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.api.PostApi;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.model.*;
import pl.jhonylemon.memewebsite.service.post.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostController implements PostApi {

    private final PostService postService;
    private final PostMapper postMapper;

    @Override
    public ResponseEntity<PostGetFullModelApi> createPublishedPost(String title, List<MultipartFile> files, Boolean visible, List<String> description,List<Long> tags) {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.createPublishedPost(
                                title,
                                files,
                                visible,
                                description,
                                tags
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostGetFullModelApi> createUnpublishedPost(PostPostModelApi postPostModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.createUnpublishedPost(
                                postMapper.postModelApiTo(postPostModelApi)
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_DELETE') or hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> deletePost(Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PostPageGetModelApi> getAllPosts(PostRequestModelApi postRequestModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToPostPageGetModelApi(
                        postService.getAllPosts(
                                postMapper.postToPostRequestDto(postRequestModelApi)
                        )
                ));
    }

    @Override
    public ResponseEntity<PostGetFullModelApi> getPost(Long id) {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.getPost(id)
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT') or hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<PostGetFullModelApi> updatePost(Long id, PostPutModelApi postPutModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.updatePost(
                                id,
                                postMapper.postModelApiTo(postPutModelApi)
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostGetFullModelApi> updatePostPublish(Long id) {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.updatePostPublish(
                                id
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostGetFullModelApi> getUnpublishedPost() {
        return ResponseEntity.ok().body(
                postMapper.postToGetFullModelApi(
                        postService.getUnpublishedPost()
                ));
    }
}
