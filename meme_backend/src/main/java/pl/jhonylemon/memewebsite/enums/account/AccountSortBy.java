package pl.jhonylemon.memewebsite.enums.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountSortBy {
    ID("id", "id"),
    DATE("date", "creationDate"),
    NAME("name", "name"),
    ENABLED("enabled","enabled"),
    BANNED("banned","banned");

    private final String requestName;
    private final String fieldName;

    public static AccountSortBy fromRequestName(String requestName) {
        return Arrays
                .stream(AccountSortBy.values())
                .filter(accountSortBy -> accountSortBy.requestName.equalsIgnoreCase(requestName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No SortBy value found for %s request name.", requestName)));
    }
}
