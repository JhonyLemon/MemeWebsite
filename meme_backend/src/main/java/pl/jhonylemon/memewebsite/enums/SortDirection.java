package pl.jhonylemon.memewebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    private final String requestName;

    public static SortDirection fromRequestName(String requestName) {
        return Arrays
                .stream(SortDirection.values())
                .filter(sortDirection -> sortDirection.requestName.equalsIgnoreCase(requestName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SortDirection value found for %s request name.", requestName)));
    }
}
