package pl.jhonylemon.memewebsite.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.enums.account.AccountSortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class AccountPagingAndSortingRequestDto {
    private int page = 0;
    private int size = 50;
    private String sortBy;
    private String sortDirection;

    @JsonIgnore
    public boolean isRequestNotComplete() {
        return sortBy == null || sortDirection == null;
    }

    public void setDefaultSorterValues() {
        sortBy = sortBy == null ? AccountSortBy.ID.getRequestName() : sortBy;
        sortDirection = sortDirection == null ? SortDirection.ASC.getRequestName() : sortDirection;
    }

    @JsonIgnore
    public boolean isPageCorrect() {
        return page >= 0;
    }

    @JsonIgnore
    public boolean isSizeCorrect() {
        return size > 0;
    }
}
