package pl.jhonylemon.memewebsite.service.post.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.PostObjectRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;
import pl.jhonylemon.memewebsite.service.post.util.PostUtil;
import pl.jhonylemon.memewebsite.service.poststatistic.user.UserPostStatisticService;

import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.post.util.PostUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class GuestPostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostObjectRepository postObjectRepository;
    private final TagRepository tagRepository;
    private final CustomUserDetailsService userDetailsService;
    private final UserPostStatisticService userPostStatisticService;

    public PostPageGetDto getAllPosts(PostRequestDto postRequestDto){
        validateRequest(postRequestDto);

        Page<Post> posts = postRepository
                .findAll(PostUtil.getSpecification(postRequestDto.getFilters()),
                        PostUtil.createPageRequest(postRequestDto.getPagingAndSorting()));

        List<PostGetShortDto> accountGetFullDtos = new ArrayList<>();

        posts.forEach(p-> {
            PostGetShortDto postGetShortDto = postMapper.postToGetShortDto(p);
            postGetShortDto.setFirstFileId(postObjectRepository
                    .findFirstByPostId(postGetShortDto.getId(), PageRequest.of(0,1))
                    .stream().findFirst().orElse(null));
            accountGetFullDtos.add(postGetShortDto);
        });


        return new PostPageGetDto(
                accountGetFullDtos,
                posts.getTotalPages(),
                posts.getTotalElements(),
                postRequestDto.getFilters());
    }

    public PostGetFullDto getPost(Long id){
        if(id == null || id<1){
            throw new PostInvalidParamException();
        }

        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostNotFoundException();
        });

        if(!post.isPublished()){
            throw new PostInvalidParamException();
        }

        try{
          Account account = userDetailsService.currentUser();
          userPostStatisticService.setSeenStatistic(account.getId(),id);
        }catch (Exception ignored){}

        PostGetFullDto postGetFullDto = postMapper.postToGetFullDto(post);

        postGetFullDto.setFilesId(postObjectRepository.findPostObjectsByPostId(post.getId()));

        postGetFullDto.getComments().removeIf(c->c.getReplyToId()!=null);

        return postGetFullDto;
    }
}
