package pl.jhonylemon.memewebsite.service.post.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.post.*;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostFile;
import pl.jhonylemon.memewebsite.exception.post.PostInvalidParamException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.TagRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
