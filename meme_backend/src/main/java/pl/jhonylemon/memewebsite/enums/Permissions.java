package pl.jhonylemon.memewebsite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {
    USER_EDIT("USER_EDIT"),
    USER_ADD("USER_ADD"),
    USER_DELETE("USER_DELETE"),
    USER_READ("USER_READ"),
    MODERATOR_EDIT("MODERATOR_EDIT"),
    MODERATOR_ADD("MODERATOR_ADD"),
    MODERATOR_READ("MODERATOR_READ"),
    MODERATOR_DELETE("MODERATOR_DELETE"),
    ADMIN_ADD("ADMIN_ADD"),
    ADMIN_READ("ADMIN_READ"),
    ADMIN_EDIT("ADMIN_EDIT"),
    ADMIN_DELETE("ADMIN_DELETE");

    private final String name;
}
