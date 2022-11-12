package pl.jhonylemon.memewebsite.service.tag.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.tag.TagPageGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagGetDto;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.exception.tag.TagInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.repository.TagRepository;
import pl.jhonylemon.memewebsite.service.tag.util.TagUtil;

import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.tag.util.TagUtil.validateRequest;


@Service
@RequiredArgsConstructor
public class GuestTagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public TagGetDto getTag(Long id){
        if(id == null || id<1){
            throw new TagInvalidParamException();
        }
        Tag tag = tagRepository.findById(id).orElseThrow(()->{
            throw new TagInvalidParamException();
        });

        return tagMapper.TagToGetDto(tag);
    }

    public TagPageGetDto getAllTags(TagRequestDto tagRequestDto){
        validateRequest(tagRequestDto);

        Page<Tag> tags = tagRepository
                .findAll(TagUtil.getSpecification(tagRequestDto.getFilters()),
                        TagUtil.createPageRequest(tagRequestDto.getPagingAndSorting()));

        List<TagGetDto> accountGetFullDtos = new ArrayList<>();

        tags.forEach(t-> accountGetFullDtos.add(tagMapper.TagToGetDto(t)));

        return new TagPageGetDto(
                accountGetFullDtos,
                tags.getTotalPages(),
                tags.getTotalElements(),
                tagRequestDto.getFilters());
    }

}






















