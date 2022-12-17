package pl.jhonylemon.memewebsite.service.post.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.component.PostProperties;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostObject;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.PostObjectRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CustomUserDetailsService userDetailsService;
    private final PostProperties postProperties;

    private final PostObjectRepository postObjectRepository;

    @Transactional
    public PostGetFullDto createPost(PostPostDto postPostDto) {
        if(!postPostDto.validateRequest()){
           throw new PostInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        if(postRepository.findUnPublishedPostsCount()>=postProperties.getMaxUnPublishedPosts()){
            throw new PostInvalidParamException("Max unpublished posts reached");
        }

        List<Tag> tags = postPostDto.getTags()==null ? new ArrayList<>() : tagRepository.findAllById(postPostDto.getTags());

        Post post = Post.builder()
                .tags(tags)
                .account(account)
                .title(postPostDto.getTitle())
                .creationDate(LocalDate.now())
                .isPublished(false)
                .isVisible(postPostDto.getVisible())
                .build();

        postRepository.save(post);

        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(post.getId()));

        return postGetFullDto;
    }

    @Transactional
    public PostGetFullDto publishPost(Long id) {
        if (id==null || id<1) {
            throw new AccountInvalidParamException();
        }

        Post post = postRepository.findUnPublishedPost().orElseThrow(()->{
            throw new PostNotFoundException();
        });

        if(!post.getFiles().isEmpty()){
            throw new PostInvalidParamException("Post must contain at least one photo");
        }

        post.isPublished(true);

        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(post.getId()));

        return postGetFullDto;
    }

    @Transactional
    public PostGetFullDto updatePostSelf(Long id, PostPutDto postPutDto){
        if(id == null || id<1){
            throw new PostInvalidParamException();
        }
        if(!postPutDto.isTittleValid()){
            throw new PostInvalidParamException();
        }
        if(!postPutDto.isTagsValid()){
            throw new PostInvalidParamException();
        }
        if(!postPutDto.isVisibleValid()){
            throw new PostInvalidParamException();
        }
        if(!postPutDto.isOrderValid()){
            throw new PostInvalidParamException();
        }

        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostNotFoundException();
        });

        Account account = userDetailsService.currentUser();

        if (!account.getId().equals(post.getAccount().getId())) {
            throw new AuthorizationFailedException();
        }

        post.isVisible(postPutDto.getVisible());
        post.setTags(tagRepository.findAllById(postPutDto.getTags()));
        post.setTitle(postPutDto.getTitle());

        postPutDto.getOrder().forEach((k,v)->{
            for(PostObject postObject : post.getFiles()){
                if(postObject.getId().equals(k)){
                    postObject.setOrder(v);
                    break;
                }
            }
        });

        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(post.getId()));

        return postGetFullDto;
    }

    @Transactional
    public void deletePostSelf(Long id){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });

        Account account = userDetailsService.currentUser();

        if (!account.getId().equals(post.getAccount().getId())) {
            throw new AuthorizationFailedException();
        }

        postRepository.delete(post);
    }

}
