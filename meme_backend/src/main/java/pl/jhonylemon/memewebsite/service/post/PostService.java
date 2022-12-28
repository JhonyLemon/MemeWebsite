package pl.jhonylemon.memewebsite.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.component.PostProperties;
import pl.jhonylemon.memewebsite.dto.comment.CommentGetDto;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.dto.post.v1.PostGetShortDto;
import pl.jhonylemon.memewebsite.dto.post.v1.PostPageGetDto;
import pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.*;
import pl.jhonylemon.memewebsite.enums.Permissions;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.authentication.NotEnoughPermissionsException;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.repository.*;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;
import pl.jhonylemon.memewebsite.service.post.util.PostUtil;
import pl.jhonylemon.memewebsite.service.poststatistic.PostStatisticService;
import pl.jhonylemon.memewebsite.util.Validator;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.jhonylemon.memewebsite.service.post.util.PostUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostObjectRepository postObjectRepository;
    private final TagRepository tagRepository;
    private final CustomUserDetailsService userDetailsService;
    private final PostStatisticService postStatisticService;
    private final PostProperties postProperties;
    private final PostObjectMapper postObjectMapper;
    private final CommentStatisticRepository commentStatisticRepository;
    private final PostStatisticRepository postStatisticRepository;

    @Transactional
    public pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto createUnpublishedPost(PostPostDto postPostDto) {
        if (!postPostDto.validateRequest()) {
            throw new PostInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        if (postRepository.findUnPublishedPostsCount() >= postProperties.getMaxUnPublishedPosts()) {
            throw new PostInvalidParamException("Max unpublished posts reached");
        }

        List<Tag> tags = postPostDto.getTags() == null ? new ArrayList<>() : tagRepository.findAllById(postPostDto.getTags());

        Post post = Post.builder()
                .tags(tags)
                .account(account)
                .title(postPostDto.getTitle())
                .creationDate(LocalDate.now())
                .files(new ArrayList<>())
                .isPublished(false)
                .comments(new ArrayList<>())
                .postStatistics(new ArrayList<>())
                .isVisible(postPostDto.getVisible())
                .build();

        postRepository.save(post);

        pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto = postMapper.postToV1GetFullDto(post);

        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));

        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToShortGetDto)
                .collect(Collectors.toList()));

        return postGetFullDto;
    }

    @Transactional
    public void deletePost(Long id) {
        if (!Validator.isIdValid(id)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostInvalidParamException();
        });

        Account account = userDetailsService.currentUser();

        Validator.checkPermission(userDetailsService.currentUserPermissions(), Map.of(
                Permissions.MODERATOR_DELETE.getName(), () -> {
                },
                Permissions.USER_DELETE.getName(), () -> {
                    if (!post.getAccount().getId().equals(account.getId())) {
                        throw new NotEnoughPermissionsException();
                    }
                }
        ));

        postObjectRepository.deleteAll(post.getFiles());
        post.getFiles().clear();
        postRepository.delete(post);
    }

    public PostPageGetDto getAllPostsWithoutContent(PostRequestDto postToPostRequestDto) {
        validateRequest(postToPostRequestDto);

        Account account = userDetailsService.currentUser();

        Page<Post> posts = postRepository
                .findAll(PostUtil.getSpecification(postToPostRequestDto.getFilters(),account==null ? null : account.getId()),
                        PostUtil.createPageRequest(postToPostRequestDto.getPagingAndSorting()));

        List<PostGetShortDto> accountGetFullDtos = new ArrayList<>();

        posts.forEach(p -> {
            PostGetShortDto postGetShortDto = postMapper.postToV1GetShortDto(p);
            postGetShortDto.setFirstObjectId(
                    postObjectRepository
                            .findFirstByPostId(postGetShortDto.getId(), PageRequest.of(0, 1))
                            .stream().findFirst()
                            .orElseThrow(() -> {
                        throw new PostObjectNotFoundException();
                    }).getId());
            postGetShortDto.setPostStatistics(setPostStatistics(account,postGetShortDto.getPostStatistics()));
            accountGetFullDtos.add(postGetShortDto);
        });

        return new PostPageGetDto(
                accountGetFullDtos,
                posts.getTotalPages(),
                posts.getTotalElements(),
                postToPostRequestDto.getFilters());
    }

    public pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto getPostWithoutContent(Long id) {
        if (!Validator.isIdValid(id)) {
            throw new PostInvalidParamException();
        }

        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        if (!post.isPublished()) {
            throw new PostInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        try {

            postStatisticService.setSeenStatistic(account.getId(), id);
        } catch (Exception ignored) {
        }

        pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto = postMapper.postToV1GetFullDto(post);

        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToShortGetDto)
                        .collect(Collectors.toList()));

        postGetFullDto.getComments().removeIf(c -> c.getReplyToId() != null);

        postGetFullDto.setComments(setCommentStatistics(account,postGetFullDto.getComments()));
        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));

        return postGetFullDto;
    }

    @Transactional
    public pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto updatePost(Long id, PostPutDto postModelApiTo) {
        if (!Validator.isIdValid(id)) {
            throw new PostInvalidParamException();
        }
        if (!postModelApiTo.isTittleValid()) {
            throw new PostInvalidParamException();
        }
        if (!postModelApiTo.isTagsValid()) {
            throw new PostInvalidParamException();
        }
        if (!postModelApiTo.isVisibleValid()) {
            throw new PostInvalidParamException();
        }
        if (!postModelApiTo.isOrderValid()) {
            throw new PostInvalidParamException();
        }

        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        Account account = userDetailsService.currentUser();

        Validator.checkPermission(userDetailsService.currentUserPermissions(), Map.of(
                Permissions.MODERATOR_EDIT.getName(), () -> {
                },
                Permissions.USER_EDIT.getName(), () -> {
                    if (!post.getAccount().getId().equals(account.getId())) {
                        throw new NotEnoughPermissionsException();
                    }
                }
        ));

        post.isVisible(postModelApiTo.getVisible());
        post.setTags(tagRepository.findAllById(postModelApiTo.getTags()));
        post.setTitle(postModelApiTo.getTitle());

        postModelApiTo.getOrder().forEach((k, v) -> {
            for (PostObject postObject : post.getFiles()) {
                if (postObject.getId().equals(k)) {
                    postObject.setOrder(v);
                    break;
                }
            }
        });

        pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto = postMapper.postToV1GetFullDto(post);

        postGetFullDto.getComments().removeIf(c -> c.getReplyToId() != null);
        postGetFullDto.setComments(setCommentStatistics(account,postGetFullDto.getComments()));
        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));

        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToShortGetDto)
                        .collect(Collectors.toList()));

        return postGetFullDto;
    }

    @Transactional
    public pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto updatePostPublish(Long id) {
        if (!Validator.isIdValid(id)) {
            throw new AccountInvalidParamException();
        }

        Post post = postRepository.findUnPublishedPost().orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        if (post.getFiles().isEmpty()) {
            throw new PostInvalidParamException("Post must contain at least one photo");
        }

        post.isPublished(true);

        pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto = postMapper.postToV1GetFullDto(post);

        Account account = userDetailsService.currentUser();

        postGetFullDto.getComments().removeIf(c -> c.getReplyToId() != null);
        postGetFullDto.setComments(setCommentStatistics(account,postGetFullDto.getComments()));
        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));
        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToShortGetDto)
                        .collect(Collectors.toList()));

        return postGetFullDto;
    }

    public pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto getUnpublishedPost() {
        Post post = postRepository.findUnPublishedPost().orElseThrow(() -> {
            throw new PostNotFoundException();
        });
        Account account = userDetailsService.currentUser();
        pl.jhonylemon.memewebsite.dto.post.v1.PostGetFullDto postGetFullDto = postMapper.postToV1GetFullDto(
                post
        );

        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));
        return postGetFullDto;
    }

    @Transactional
    public pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto createPublishedPost(String title, List<MultipartFile> files, Boolean visible, List<String> descriptions,List<Long> tags) {
        if(!Validator.isStringValid(title)){
           throw new PostInvalidParamException();
        }
        if(files==null || files.isEmpty()){
            throw new PostInvalidParamException();
        }
        if(descriptions==null){
            descriptions = new ArrayList<>();
        }
        if(visible==null){
            visible = true;
        }

        Account account = userDetailsService.currentUser();

        List<Tag> tagsEntities = tags == null ? new ArrayList<>() : tagRepository.findAllById(tags);

        Post post = Post.builder()
                .tags(tagsEntities)
                .account(account)
                .title(title)
                .creationDate(LocalDate.now())
                .files(new ArrayList<>())
                .isPublished(false)
                .comments(new ArrayList<>())
                .postStatistics(new ArrayList<>())
                .isVisible(visible)
                .build();

        postRepository.save(post);

        try {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String description = descriptions.size() > i ? descriptions.get(i) : null;
                PostObject object = PostObject.builder()
                        .order(1L)
                        .post(post)
                        .description(description)
                        .fileName(file.getOriginalFilename())
                        .mimeType(file.getContentType())
                        .content(file.getBytes())
                        .build();
                postObjectRepository.save(object);
                post.getFiles().add(object);
            }
        }catch (Exception e){
            throw new PostInvalidParamException();
        }

        pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto postGetFullDto = postMapper.postToV2GetFullDto(post);

        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));

        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToFullGetDto)
                        .collect(Collectors.toList()));

        return postGetFullDto;

    }

    public PostGetFullDto getPostWithContent(Long id) {
        if (!Validator.isIdValid(id)) {
            throw new PostInvalidParamException();
        }

        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new PostNotFoundException();
        });

        if (!post.isPublished()) {
            throw new PostInvalidParamException();
        }

        try {
            Account account = userDetailsService.currentUser();
            postStatisticService.setSeenStatistic(account.getId(), id);
        } catch (Exception ignored) {
        }

        Account account = userDetailsService.currentUser();

        pl.jhonylemon.memewebsite.dto.post.v2.PostGetFullDto postGetFullDto = postMapper.postToV2GetFullDto(post);


        postGetFullDto.getComments().removeIf(c -> c.getReplyToId() != null);
        postGetFullDto.setComments(setCommentStatistics(account,postGetFullDto.getComments()));
        postGetFullDto.setPostStatistics(setPostStatistics(account,postGetFullDto.getPostStatistics()));

        postGetFullDto.setPostObjects(
                postObjectRepository.findPostObjectsByPostId(post.getId()).stream()
                        .map(postObjectMapper::postObjectToFullGetDto)
                        .collect(Collectors.toList()));

        return postGetFullDto;
    }

    public pl.jhonylemon.memewebsite.dto.post.v2.PostPageGetDto getAllPostsWithContent(PostRequestDto postToPostRequestDto) {
        validateRequest(postToPostRequestDto);

        Account account = userDetailsService.currentUser();

        Page<Post> posts = postRepository
                .findAll(PostUtil.getSpecification(postToPostRequestDto.getFilters(), account==null ? null : account.getId()),
                        PostUtil.createPageRequest(postToPostRequestDto.getPagingAndSorting()));

        List<pl.jhonylemon.memewebsite.dto.post.v2.PostGetShortDto> accountGetFullDtos = new ArrayList<>();


        posts.forEach(p -> {
            pl.jhonylemon.memewebsite.dto.post.v2.PostGetShortDto postGetShortDto = postMapper.postToV2GetShortDto(p);
            postGetShortDto.setFirstObjectContent(
                    postObjectRepository
                            .findFirstByPostId(postGetShortDto.getId(), PageRequest.of(0, 1))
                            .stream().findFirst()
                            .orElseThrow(() -> {
                                throw new PostObjectNotFoundException();
                            }).getContent());

            postGetShortDto.setPostStatistics(setPostStatistics(account,postGetShortDto.getPostStatistics()));
            accountGetFullDtos.add(postGetShortDto);
        });

        return new pl.jhonylemon.memewebsite.dto.post.v2.PostPageGetDto(
                accountGetFullDtos,
                posts.getTotalPages(),
                posts.getTotalElements(),
                postToPostRequestDto.getFilters());
    }

    private List<CommentGetDto> setCommentStatistics(Account account, List<CommentGetDto> commentGetDto){
        if(account!=null){
            for(var comment : commentGetDto){
                if(comment.getChildComments()!=null && !comment.getChildComments().isEmpty()) {
                    comment.setChildComments(setCommentStatistics(account, comment.getChildComments()));
                }
                comment.getCommentStatistics().expandData(commentStatisticRepository.findByComment_IdAndAccount_Id(
                        comment.getId(),
                        account.getId()
                ));
            }
        }
        return commentGetDto;
    }

    private PostStatisticGetDto setPostStatistics(Account account, PostStatisticGetDto post){
        if(account!=null){
            post.expandData(postStatisticRepository.findByPost_IdAndAccount_Id(
                    post.getPostId(),
                    account.getId()
            ));
        }
        return post;
    }

}
