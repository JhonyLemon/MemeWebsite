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
    public ResponseEntity<PostGetFullV2ModelApi> createPublishedPost(String title, List<MultipartFile> files, Boolean visible, List<String> description,List<Long> tags) {
        return ResponseEntity.ok().body(
                postMapper.postToV2GetFullModelApi(
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
    public ResponseEntity<PostGetFullV1ModelApi> createUnpublishedPost(PostPostModelApi postPostModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToV1GetFullModelApi(
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
    @PreAuthorize("hasAuthority('USER_EDIT') or hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<PostGetFullV1ModelApi> updatePost(Long id, PostPutModelApi postPutModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToV1GetFullModelApi(
                        postService.updatePost(
                                id,
                                postMapper.postModelApiTo(postPutModelApi)
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostGetFullV1ModelApi> updatePostPublish(Long id) {
        return ResponseEntity.ok().body(
                postMapper.postToV1GetFullModelApi(
                        postService.updatePostPublish(
                                id
                        )
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('USER_ADD')")
    public ResponseEntity<PostGetFullV1ModelApi> getUnpublishedPost() {
        return ResponseEntity.ok().body(
                postMapper.postToV1GetFullModelApi(
                        postService.getUnpublishedPost()
                ));
    }

    @Override
    public ResponseEntity<PostPageGetV2ModelApi> getAllPostsWithContent(PostRequestModelApi postRequestModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToV2PostPageGetModelApi(
                        postService.getAllPostsWithContent(
                                postMapper.postToPostRequestDto(postRequestModelApi)
                        )
                ));
    }

    @Override
    public ResponseEntity<PostPageGetV1ModelApi> getAllPostsWithoutContent(PostRequestModelApi postRequestModelApi) {
        return ResponseEntity.ok().body(
                postMapper.postToV1PostPageGetModelApi(
                        postService.getAllPostsWithoutContent(
                                postMapper.postToPostRequestDto(postRequestModelApi)
                        )
                ));
    }

    @Override
    public ResponseEntity<PostGetFullV2ModelApi> getPostWithContent(Long id) {
        return ResponseEntity.ok().body(
                postMapper.postToV2GetFullModelApi(
                        postService.getPostWithContent(id)
                ));
    }

    @Override
    public ResponseEntity<PostGetFullV1ModelApi> getPostWithoutContent(Long id) {
        return ResponseEntity.ok().body(
                postMapper.postToV1GetFullModelApi(
                        postService.getPostWithoutContent(id)
                ));
    }
}
