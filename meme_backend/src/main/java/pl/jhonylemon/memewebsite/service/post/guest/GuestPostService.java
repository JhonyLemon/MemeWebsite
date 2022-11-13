package pl.jhonylemon.memewebsite.service.post.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.PostFileRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.service.post.util.PostUtil;

import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.post.util.PostUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class GuestPostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final TagRepository tagRepository;

    public PostPageGetDto getAllPosts(PostRequestDto postRequestDto){
        validateRequest(postRequestDto);

        Page<Post> posts = postRepository
                .findAll(PostUtil.getSpecification(postRequestDto.getFilters()),
                        PostUtil.createPageRequest(postRequestDto.getPagingAndSorting()));

        List<PostGetShortDto> accountGetFullDtos = new ArrayList<>();

        posts.forEach(p-> accountGetFullDtos.add(postMapper.postToGetShortDto(p)));

        return new PostPageGetDto(
                accountGetFullDtos,
                posts.getTotalPages(),
                posts.getTotalElements(),
                postRequestDto.getFilters());
    }

    public PostGetFullDto getPost(Long id){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }

        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });

        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.getComments().removeIf(c->c.getReplyToId()!=null);

        return postGetFullDto;
    }
}
