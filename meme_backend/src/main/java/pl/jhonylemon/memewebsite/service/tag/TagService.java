package pl.jhonylemon.memewebsite.service.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagPageGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.tag.TagInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.service.tag.util.TagUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.tag.util.TagUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Transactional
    public TagGetDto addTag(String tag) {
        if(tag == null || tag.isEmpty()){
            throw new PostNotFoundException();
        }
        Tag tagEntity = Tag.builder()
                .tag(tag)
                .build();

        tagRepository.save(tagEntity);

        return tagMapper.tagToGetDto(tagEntity);
    }

    @Transactional
    public TagGetDto changeTag(Long id, String tag) {
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Tag tagEntity = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });
        tagEntity.setTag(tag);
        return tagMapper.tagToGetDto(tagEntity);
    }

    @Transactional
    public void deleteTag(Long id) {
        if(id == null || id<1){
            throw new PostNotFoundException();
        }
        Tag tagEntity = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });
        tagEntity.getPosts().forEach(p->p.getTags().remove(tagEntity));
        tagRepository.delete(tagEntity);
    }

    public TagPageGetDto getAllTags(TagRequestDto tagRequestDto) {
        validateRequest(tagRequestDto);

        Page<Tag> tags = tagRepository
                .findAll(TagUtil.getSpecification(tagRequestDto.getFilters()),
                        TagUtil.createPageRequest(tagRequestDto.getPagingAndSorting()));

        List<TagGetDto> accountGetFullDtos = new ArrayList<>();

        tags.forEach(t-> accountGetFullDtos.add(tagMapper.tagToGetDto(t)));

        return new TagPageGetDto(
                accountGetFullDtos,
                tags.getTotalPages(),
                tags.getTotalElements(),
                tagRequestDto.getFilters());
    }

    public TagGetDto getTag(Long id) {
        if(id == null || id<1){
            throw new TagInvalidParamException();
        }
        Tag tag = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });

        return tagMapper.tagToGetDto(tag);
    }
}
