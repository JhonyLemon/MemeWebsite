package pl.jhonylemon.memewebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SortBy {
    ID("id", "id"),
    DATE("date", "creationDate");

    private final String requestName;
    private final String fieldName;

    public static SortBy fromRequestName(String requestName) {
        return Arrays
                .stream(SortBy.values())
                .filter(sortBy -> sortBy.requestName.equalsIgnoreCase(requestName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SortBy value found for %s request name.", requestName)));
    }
}
