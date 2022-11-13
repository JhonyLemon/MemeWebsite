package pl.jhonylemon.memewebsite.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {

    private TagPagingAndSortingRequestDto pagingAndSorting;
    private TagFilterDto filters;

    public boolean hasPagingAndSorting() {
        return pagingAndSorting != null;
    }

    public void setDefaultPagingAndSorting() {
        pagingAndSorting = new TagPagingAndSortingRequestDto();
    }

}
