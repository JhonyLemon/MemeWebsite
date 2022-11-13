package pl.jhonylemon.memewebsite.dto.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.enums.tag.TagSortBy;
import pl.jhonylemon.memewebsite.enums.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class TagPagingAndSortingRequestDto {
    private int page = 0;
    private int size = 50;
    private String sortBy;
    private String sortDirection;

    @JsonIgnore
    public boolean isRequestNotComplete() {
        return sortBy == null || sortDirection == null;
    }

    public void setDefaultSorterValues() {
        sortBy = sortBy == null ? TagSortBy.ID.getRequestName() : sortBy;
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
