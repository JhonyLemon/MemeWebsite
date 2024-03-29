package pl.jhonylemon.memewebsite.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private AccountPagingAndSortingRequestDto pagingAndSorting;
    private AccountFilterDto filters;

    public boolean hasPagingAndSorting() {
        return pagingAndSorting != null;
    }

    public void setDefaultPagingAndSorting() {
        pagingAndSorting = new AccountPagingAndSortingRequestDto();
    }

}
