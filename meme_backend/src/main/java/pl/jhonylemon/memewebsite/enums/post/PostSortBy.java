package pl.jhonylemon.memewebsite.enums.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PostSortBy {
    ID("id", "id"),
    TITLE("title", "title");

    private final String requestName;
    private final String fieldName;

    public static PostSortBy fromRequestName(String requestName) {
        return Arrays
                .stream(PostSortBy.values())
                .filter(sortBy -> sortBy.requestName.equalsIgnoreCase(requestName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SortBy value found for %s request name.", requestName)));
    }
}
