package pl.jhonylemon.memewebsite.service.account.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import pl.jhonylemon.memewebsite.dto.PagingAndSortingRequestDto;
import pl.jhonylemon.memewebsite.dto.account.AccountFilterDto;
import pl.jhonylemon.memewebsite.dto.account.AccountRequestDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.enums.SortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;
import pl.jhonylemon.memewebsite.exception.BusinessException;

import static pl.jhonylemon.memewebsite.service.account.util.AccountSpecification.*;

public class AccountUtil {
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

    public static Specification<Account> getSpecification(AccountFilterDto accountFilterDto) {
        return accountFilterDto != null ?
                hasCreationDate(accountFilterDto.getCreationDate())
                        .and(hasNumberOfPosts(accountFilterDto.getPosts()))
                : Specification.where(null);
    }

    public static void validateRequest(AccountRequestDto accountRequestDto) {
        if (!accountRequestDto.hasPagingAndSorting()) {
            accountRequestDto.setDefaultPagingAndSorting();
        } else if (!accountRequestDto.getPagingAndSorting().isPageCorrect() ||
                !accountRequestDto.getPagingAndSorting().isSizeCorrect()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid paging request");
        }
    }
}
