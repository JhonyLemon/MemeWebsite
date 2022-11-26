package pl.jhonylemon.memewebsite.service.post.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.AccountGetShortDto;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostFile;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

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
    private final AccountRepository accountRepository;
    private final GuestAccountService guestAccountService;

    public PostGetFullDto createPost(Long id,PostPostDto postPostDto) {
        if(!postPostDto.validateRequest()){
           throw new PostInvalidParamException();
        }
        if (id==null || id<1) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        List<Tag> tags = postPostDto.getTags()==null ? new ArrayList<>() : tagRepository.findAllById(postPostDto.getTags());

        List<PostFile> files = new ArrayList<>();

        try {
            for (int i = 0; i < postPostDto.getFiles().size(); i++) {
                files.add(PostFile.builder()
                        .description(postPostDto.getDescriptions().get(i))
                        .file(postPostDto.getFiles().get(i).getBytes())
                        .fileName(postPostDto.getFiles().get(i).getName())
                        .mimeType(postPostDto.getFiles().get(i).getContentType())
                        .build());
            }
        }catch (Exception e){
            throw new PostInvalidParamException();
        }

        Post post = Post.builder()
                .tags(tags)
                .account(account)
                .title(postPostDto.getTitle())
                .files(files)
                .creationDate(LocalDate.now())
                .visible(postPostDto.getVisible())
                .build();

        postRepository.save(post);
        return postMapper.postToGetFullDto(post);
    }

    @Transactional
    public PostGetFullDto updatePostSelf(Long id, PostPutDto postPutDto){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        if (!authAccount.getId().equals(post.getAccount().getId())) {
            throw new AuthorizationFailedException();
        }

        if(postPutDto.getVisible()!=null){
            post.setVisible(postPutDto.getVisible());
        }
        if(postPutDto.getTags()!=null){
            post.setTags(tagRepository.findAllById(postPutDto.getTags()));
        }
        if(postPutDto.getDescriptions()!=null && postPutDto.getFiles()!=null && postPutDto.getFiles().size()==postPutDto.getDescriptions().size()){
            List<PostFile> files = new ArrayList<>();
            try {
                for (int i = 0; i < postPutDto.getFiles().size(); i++) {
                    files.add(PostFile.builder()
                            .description(postPutDto.getDescriptions().get(i))
                            .file(postPutDto.getFiles().get(i).getBytes())
                            .fileName(postPutDto.getFiles().get(i).getName())
                            .mimeType(postPutDto.getFiles().get(i).getContentType())
                            .build());
                }
            }catch (Exception e){
                throw new PostInvalidParamException();
            }
            post.setFiles(files);
        }
        if(postPutDto.getTitle()!=null){
            post.setTitle(postPutDto.getTitle());
        }
        return postMapper.postToGetFullDto(post);
    }

    @Transactional
    public void deletePostSelf(Long id){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).orElseThrow(()->{
            throw new PostInvalidParamException();
        });

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        if (!authAccount.getId().equals(post.getAccount().getId())) {
            throw new AuthorizationFailedException();
        }

        postRepository.delete(post);
    }

}
