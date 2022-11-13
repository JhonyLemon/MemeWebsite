package pl.jhonylemon.memewebsite.enums.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TagSortBy {
    ID("id", "id"),
    TAG("tag", "tag");

    private final String requestName;
    private final String fieldName;

    public static TagSortBy fromRequestName(String requestName) {
        return Arrays
                .stream(TagSortBy.values())
                .filter(sortBy -> sortBy.requestName.equalsIgnoreCase(requestName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SortBy value found for %s request name.", requestName)));
    }
}
