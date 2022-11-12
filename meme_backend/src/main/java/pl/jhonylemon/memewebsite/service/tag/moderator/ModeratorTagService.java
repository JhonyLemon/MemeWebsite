package pl.jhonylemon.memewebsite.service.tag.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.tag.TagInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.repository.TagRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ModeratorTagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Transactional
    public TagGetDto addTag(String tag){
        if(tag == null || tag.isEmpty()){
            throw new PostNotFoundException();
        }
        Tag tagEntity = Tag.builder()
                .tag(tag)
                .build();

        tagRepository.save(tagEntity);

        return tagMapper.TagToGetDto(tagEntity);
    }
    @Transactional
    public TagGetDto changeTag(Long id,String tag){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Tag tagEntity = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });
        tagEntity.setTag(tag);
        return tagMapper.TagToGetDto(tagEntity);
    }
    @Transactional
    public void deleteTag(Long id){
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Tag tagEntity = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });
        tagEntity.getPosts().forEach(p->p.getTags().remove(tagEntity));
        tagRepository.delete(tagEntity);
    }

}






















