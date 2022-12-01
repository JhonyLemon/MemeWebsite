package pl.jhonylemon.memewebsite.service.postobject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.component.PostProperties;
import pl.jhonylemon.memewebsite.dto.post.PostGetFullDto;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostObject;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.repository.PostObjectRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;

import javax.servlet.ServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserPostObjectService {

    private final PostObjectMapper postObjectMapper;

    private final PostMapper postMapper;
    private final PostObjectRepository postObjectRepository;
    private final PostRepository postRepository;
    private final PostProperties postProperties;


    @Transactional
    public PostGetFullDto createPostObject(Long id, Long order, MultipartFile file) {
        if (id == null || id < 1) {
            throw new PostInvalidParamException();
        }

        if (file.getSize() > postProperties.getMaxFileObject()) {
            throw new PostInvalidParamException("File too large");
        }

        if (!postProperties.getAllowedTypes().contains(file.getContentType())) {
            throw new PostInvalidParamException("Unsupported media type");
        }

        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        if (post.getFiles().size() >= postProperties.getMaxObjects()) {
            throw new PostInvalidParamException("Max post size reached");
        }

        PostObject postObject = PostObject.builder()
                .post(post)
                .order(order)
                .build();

        try {
            postObject.setContent(file.getBytes());
            postObject.setFileName(file.getOriginalFilename());
            postObject.setMimeType(file.getContentType());
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        postObjectRepository.save(postObject);
        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(post.getId()));

        return postGetFullDto;
    }

    @Transactional
    public PostGetFullDto updatePostObjectSelf(Long id, MultipartFile file) {
        if (id == null || id < 1) {
            throw new PostInvalidParamException();
        }

        if (file.getSize() > postProperties.getMaxFileObject()) {
            throw new PostInvalidParamException("File too large");
        }

        PostObject postObject = postObjectRepository.findById(id).orElseThrow(() -> {
            throw new PostObjectNotFoundException();
        });

        try {
            postObject.setContent(file.getBytes());
            postObject.setFileName(file.getOriginalFilename());
            postObject.setMimeType(file.getContentType());
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        postObjectRepository.save(postObject);

        PostGetFullDto postGetFullDto = postMapper
                .postToGetFullDto(
                        postRepository.findById(id).orElseThrow(() -> {
                            throw new PostNotFoundException();
                        }));

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(postGetFullDto.getId()));

        return postGetFullDto;

    }

    @Transactional
    public void deletePostObjectSelf(Long id) {
        if (id == null || id < 1) {
            throw new PostInvalidParamException();
        }

        PostObject postObject = postObjectRepository.findById(id).orElseThrow(() -> {
            throw new PostObjectNotFoundException();
        });

        postObjectRepository.delete(postObject);
    }

    @Transactional
    public PostGetFullDto createPostObject(Long id, Long order, String content, ServletRequest request) {
        if (id == null || id < 1) {
            throw new PostInvalidParamException();
        }

        if (content.length() > postProperties.getMaxTextObject()) {
            throw new PostInvalidParamException("Text too long");
        }

        if (!postProperties.getAllowedTypes().contains(request.getContentType())) {
            throw new PostInvalidParamException("Unsupported media type");
        }

        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        if (post.getFiles().size() >= postProperties.getMaxObjects()) {
            throw new PostInvalidParamException("Max post size reached");
        }

        PostObject postObject = PostObject.builder()
                .post(post)
                .order(order)
                .build();

        try {
            postObject.setContent(content.getBytes(request.getCharacterEncoding()));
            postObject.setFileName(null);
            postObject.setCharset(request.getCharacterEncoding());
            postObject.setMimeType(request.getContentType());
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        postObjectRepository.save(postObject);
        PostGetFullDto postGetFullDto = postMapper
                .postToGetFullDto(
                        postRepository.findById(id).orElseThrow(() -> {
                            throw new PostNotFoundException();
                        }));

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(postGetFullDto.getId()));

        return postGetFullDto;
    }

    @Transactional
    public PostGetFullDto updatePostObject(Long id, String content, ServletRequest request) {
        if (id == null || id < 1) {
            throw new PostInvalidParamException();
        }

        if (content.length() > postProperties.getMaxTextObject()) {
            throw new PostInvalidParamException("Text too long");
        }

        PostObject postObject = postObjectRepository.findById(id).orElseThrow(() -> {
            throw new PostObjectNotFoundException();
        });

        try {
            postObject.setContent(content.getBytes(request.getCharacterEncoding()));
            postObject.setFileName(null);
            postObject.setCharset(request.getCharacterEncoding());
            postObject.setMimeType(request.getContentType());
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        postObjectRepository.save(postObject);

        PostGetFullDto postGetFullDto = postMapper
                .postToGetFullDto(
                        postRepository.findById(id).orElseThrow(() -> {
                            throw new PostNotFoundException();
                        }));

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(postGetFullDto.getId()));

        return postGetFullDto;
    }


}