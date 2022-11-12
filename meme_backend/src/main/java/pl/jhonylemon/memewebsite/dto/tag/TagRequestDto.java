package pl.jhonylemon.memewebsite.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.PagingAndSortingRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {

    private PagingAndSortingRequestDto pagingAndSorting;
    private TagFilterDto filters;

    public boolean hasPagingAndSorting() {
        return pagingAndSorting != null;
    }

    public void setDefaultPagingAndSorting() {
        pagingAndSorting = new PagingAndSortingRequestDto();
    }

}
