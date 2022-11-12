package pl.jhonylemon.memewebsite.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jhonylemon.memewebsite.dto.PagingAndSortingRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private PagingAndSortingRequestDto pagingAndSorting;
    private PostFilterDto filters;

    public boolean hasPagingAndSorting() {
        return pagingAndSorting != null;
    }

    public void setDefaultPagingAndSorting() {
        pagingAndSorting = new PagingAndSortingRequestDto();
    }

}
