package pl.jhonylemon.memewebsite.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.PagingAndSortingRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private PagingAndSortingRequestDto pagingAndSorting;
    private AccountFilterDto filters;

    public boolean hasPagingAndSorting() {
        return pagingAndSorting != null;
    }

    public void setDefaultPagingAndSorting() {
        pagingAndSorting = new PagingAndSortingRequestDto();
    }

}
