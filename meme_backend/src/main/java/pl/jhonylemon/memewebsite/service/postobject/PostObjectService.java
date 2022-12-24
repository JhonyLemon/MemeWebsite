package pl.jhonylemon.memewebsite.service.postobject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.component.PostProperties;
import pl.jhonylemon.memewebsite.dto.postobject.PostObjectFullGetDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostObject;
import pl.jhonylemon.memewebsite.enums.Permissions;
import pl.jhonylemon.memewebsite.exception.authentication.NotEnoughPermissionsException;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectInvalidParamException;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.repository.PostObjectRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;
import pl.jhonylemon.memewebsite.util.Validator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostObjectService {

    private final PostObjectMapper postObjectMapper;
    private final PostRepository postRepository;
    private final PostObjectRepository postObjectRepository;
    private final PostProperties postProperties;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public PostObjectFullGetDto createPostObject(Long id, Long order, MultipartFile file, String description){
        if(!Validator.isIdValid(id)) {
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
            postObject.setDescription(description);
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        postObjectRepository.save(postObject);

        return postObjectMapper.postObjectToFullGetDto(postObject);
    }

    @Transactional
    public void deletePostObject(Long id){
        if (!Validator.isIdValid(id)) {
            throw new PostObjectInvalidParamException();
        }

        PostObject postObject = postObjectRepository.findById(id).orElseThrow(() -> {
            throw new PostObjectNotFoundException();
        });

        Account account = customUserDetailsService.currentUser();
        List<String> permissions = customUserDetailsService.currentUserPermissions();

        Validator.checkPermission(permissions, Map.of(
                Permissions.MODERATOR_DELETE.getName(), () -> postObjectRepository.delete(postObject),
                Permissions.USER_DELETE.getName(), () -> {
                    if(postObject.getPost().getAccount().getId().equals(account.getId())){
                        postObjectRepository.delete(postObject);
                    }else{
                        throw new NotEnoughPermissionsException();
                    }
                }
        ));
    }

    public PostObjectFullGetDto getPostObject(Long id){
        if (!Validator.isIdValid(id)) {
            throw new PostObjectInvalidParamException();
        }

        return postObjectMapper.postObjectToFullGetDto(postObjectRepository.findById(id).orElseThrow(()->{
            throw new PostObjectNotFoundException();
        }));
    }

    @Transactional
    public PostObjectFullGetDto updatePostObject(Long id, MultipartFile file, String description) {
        if (!Validator.isIdValid(id)) {
            throw new PostInvalidParamException();
        }

        if (file.getSize() > postProperties.getMaxFileObject()) {
            throw new PostInvalidParamException("File too large");
        }

        PostObject postObject = postObjectRepository.findById(id).orElseThrow(() -> {
            throw new PostObjectNotFoundException();
        });

        Account account = customUserDetailsService.currentUser();

        Validator.checkPermission(customUserDetailsService.currentUserPermissions(), Map.of(
                Permissions.MODERATOR_DELETE.getName(), () -> {},
                Permissions.USER_DELETE.getName(), () -> {
                    if(!postObject.getPost().getAccount().getId().equals(account.getId())){
                        throw new NotEnoughPermissionsException();
                    }
                }
        ));

        try {
            postObject.setContent(file.getBytes());
            postObject.setFileName(file.getOriginalFilename());
            postObject.setMimeType(file.getContentType());
            postObject.setDescription(description);
        } catch (Exception e) {
            throw new PostInvalidParamException();
        }

        return postObjectMapper.postObjectToFullGetDto(postObject);
    }

}
