package pl.jhonylemon.memewebsite.service.post.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ModeratorPostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Transactional
    public PostGetFullDto updatePost(Long id, PostPutDto postPutDto){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });
        return postMapper.postToGetFullDto(post);
    }

    @Transactional
    public void deletePost(Long id){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });
        postRepository.delete(post);
    }

}
