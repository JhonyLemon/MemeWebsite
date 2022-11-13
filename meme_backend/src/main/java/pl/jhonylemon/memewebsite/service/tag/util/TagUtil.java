package pl.jhonylemon.memewebsite.service.tag.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.dto.tag.TagPagingAndSortingRequestDto;
import pl.jhonylemon.memewebsite.dto.tag.TagFilterDto;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.entity.Tag;
import pl.jhonylemon.memewebsite.enums.tag.TagSortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;
import pl.jhonylemon.memewebsite.exception.BusinessException;

import static pl.jhonylemon.memewebsite.service.tag.util.TagSpecification.hasTagLike;


public class TagUtil {
    public static PageRequest createPageRequest(TagPagingAndSortingRequestDto requestDto) {
        return PageRequest.of(requestDto.getPage(), requestDto.getSize(),
                getSorter(requestDto));
    }

    private static Sort getSorter(TagPagingAndSortingRequestDto requestDto) {
        if (requestDto.isRequestNotComplete()) {
            requestDto.setDefaultSorterValues();
        }

        TagSortBy sortBy = TagSortBy.fromRequestName(requestDto.getSortBy());
        SortDirection sortDirection = SortDirection.fromRequestName(requestDto.getSortDirection());

        return sortDirection.equals(SortDirection.ASC) ?
                Sort.by(sortBy.getFieldName()).ascending() :
                Sort.by(sortBy.getFieldName()).descending();
    }

    public static Specification<Tag> getSpecification(TagFilterDto tagFilterDto) {
        return tagFilterDto != null ?
                hasTagLike(tagFilterDto.getTag())
                : Specification.where(null);
    }

    public static void validateRequest(TagRequestDto tagRequestDto) {
        if (!tagRequestDto.hasPagingAndSorting()) {
            tagRequestDto.setDefaultPagingAndSorting();
        } else if (!tagRequestDto.getPagingAndSorting().isPageCorrect() ||
                !tagRequestDto.getPagingAndSorting().isSizeCorrect()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid paging request");
        }
    }
}
