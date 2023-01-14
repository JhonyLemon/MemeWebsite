package pl.jhonylemon.memewebsite.controller.routes;

public class ApiPaths {

    public static class Version {
        public static final String v1 = "/v1";
        public static final String v2 = "/v2";
    }

    public static class Account {
        public static final String ACCOUNT_PATH = "/account";
        public static final String ACCOUNT_ID = "/{id}";
        public static final String ACCOUNT_GET_ALL_PAGINATED = "/all";
        public static final String ACCOUNT_ROLE = "/role";
        public static final String ACCOUNT_REGISTER = "/register";
        public static final String ACCOUNT_REFRESH = "/refresh";
        public static final String ACCOUNT_UPDATE_ROLE = "/role/{newRoleId}";
        public static final String ACCOUNT_UPDATE_BAN = "/ban/{ban}";
        public static final String ACCOUNT_UPDATE_NAME = "/name/{name}";
        public static final String ACCOUNT_UPDATE_PASSWORD = "/password";
        public static final String ACCOUNT_UPDATE_PHOTO = "/photo/{photoId}";
    }

    public static class Post {
        public static final String POST_PATH = "/post";
        public static final String POST_ID = "/{id}";
        public static final String POST_ALL = "/all";
    }

    public static class PostObject {
        public static final String POST_OBJECT_PATH = "/postobject";
        public static final String POST_OBJECT_ID = "/{id}";
        public static final String POST_OBJECT_ORDER = "/{order}";
    }

    public static class ProfilePicture {
        public static final String PROFILE_PICTURE_PATH = "/profilepicture";
        public static final String PROFILE_PICTURE_ID = "/{id}";
        public static final String PROFILE_PICTURE_GET_ALL = "/all";
    }

    public static class PostStatistic {
        public static final String POST_STATISTIC_PATH = "/poststatistic";
        public static final String POST_STATISTIC_SET_VOTE = "/vote";
        public static final String POST_STATISTIC_SET_FAVORITE = "/favorite";
    }

    public static class Tag {
        public static final String TAG_PATH = "/tag";
        public static final String TAG_ID = "/{id}";
        public static final String TAG_TAG = "/{tag}";
        public static final String TAG_GET_ALL_PAGINATED = "/all";
    }

    public static class AccountPermission {
        public static final String ACCOUNT_PERMISSION_PATH = "/accountpermission";
        public static final String ACCOUNT_PERMISSION_ID = "/{id}";
        public static final String ACCOUNT_PERMISSION_GET_ALL_PAGINATED = "/all";
    }

    public static class AccountRole {
        public static final String ACCOUNT_ROLE_PATH = "/accountrole";
        public static final String ACCOUNT_ROLE_ID = "/{id}";
        public static final String ACCOUNT_ROLE_PUT_DEFAULT = "/default";
        public static final String ACCOUNT_ROLE_GET_ALL = "/all";
    }

    public static class Comment {
        public static final String COMMENT_PATH = "/comment";
        public static final String COMMENT_ID = "/{id}";
    }

    public static class CommentStatistic {
        public static final String COMMENT_STATISTIC_PATH = "/commentstatistic";
        public static final String POST_STATISTIC_SET_VOTE = "";
    }
}
