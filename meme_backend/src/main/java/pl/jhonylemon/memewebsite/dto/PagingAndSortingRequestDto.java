package pl.jhonylemon.memewebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.enums.SortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class PagingAndSortingRequestDto {
    private int page = 0;
    private int size = 50;
    private String sortBy;
    private String sortDirection;

    @JsonIgnore
    public boolean isRequestNotComplete() {
        return sortBy == null || sortDirection == null;
    }

    public void setDefaultSorterValues() {
        sortBy = sortBy == null ? SortBy.ID.getRequestName() : sortBy;
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
