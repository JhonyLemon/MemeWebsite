package pl.jhonylemon.memewebsite.service.post.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.dto.PagingAndSortingRequestDto;
import pl.jhonylemon.memewebsite.dto.post.PostFilterDto;
import pl.jhonylemon.memewebsite.dto.post.PostRequestDto;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.enums.SortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;
import pl.jhonylemon.memewebsite.exception.BusinessException;

import static pl.jhonylemon.memewebsite.service.post.util.PostSpecification.hasCreationDate;


public class PostUtil {
    public static PageRequest createPageRequest(PagingAndSortingRequestDto requestDto) {
        return PageRequest.of(requestDto.getPage(), requestDto.getSize(),
                getSorter(requestDto));
    }

    private static Sort getSorter(PagingAndSortingRequestDto requestDto) {
        if (requestDto.isRequestNotComplete()) {
            requestDto.setDefaultSorterValues();
        }

        SortBy sortBy = SortBy.fromRequestName(requestDto.getSortBy());
        SortDirection sortDirection = SortDirection.fromRequestName(requestDto.getSortDirection());

        return sortDirection.equals(SortDirection.ASC) ?
                Sort.by(sortBy.getFieldName()).ascending() :
                Sort.by(sortBy.getFieldName()).descending();
    }

    public static Specification<Post> getSpecification(PostFilterDto accountFilterDto) {
        return accountFilterDto != null ?
                hasCreationDate(accountFilterDto.getCreationDate())
                        //.and(hasNumberOfPosts(accountFilterDto.getPosts()))
                : Specification.where(null);
    }

    public static void validateRequest(PostRequestDto postRequestDto) {
        if (!postRequestDto.hasPagingAndSorting()) {
            postRequestDto.setDefaultPagingAndSorting();
        } else if (!postRequestDto.getPagingAndSorting().isPageCorrect() ||
                !postRequestDto.getPagingAndSorting().isSizeCorrect()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid paging request");
        }
    }
}
