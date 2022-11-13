package pl.jhonylemon.memewebsite.controller.routes;

public class ApiPaths {


    public static class Admin {
        public static final String ADMIN_PATH = "/admin";
    }

    public static class Moderator {
        public static final String MODERATOR_PATH = "/moderator";
    }

    public static class User {
        public static final String USER_PATH = "/user";
    }

    public static class Guest {
        public static final String GUEST_PATH = "/guest";
    }

    public static class Account {
        public static final String ACCOUNT_PATH = "/account";
        public static final String ACCOUNT_GET = "/{id}";
        public static final String ACCOUNT_GET_ALL_PAGINATED = "/all";
        public static final String ACCOUNT_CREATE = "/register";
        public static final String ACCOUNT_UPDATE_PASSWORD = "/{id}/password";
        public static final String ACCOUNT_UPDATE_NAME = "/{id}/name/{name}";
        public static final String ACCOUNT_UPDATE_PROFILE_PHOTO = "/{id}/photo/{photoId}";
        public static final String ACCOUNT_DELETE = "/{id}";
        public static final String ACCOUNT_GET_PERMISSION = "/{id}/permission";
        public static final String ACCOUNT_UPDATE_PERMISSION = "/{id}/permission";
        public static final String ACCOUNT_ADD_PERMISSION = "/{id}/permission/{permissionId}";
        public static final String ACCOUNT_DELETE_PERMISSION = "/{id}/permission/{permissionId}";
        public static final String ACCOUNT_BAN = "/{id}/ban/{ban}";
    }

    public static class Post {
        public static final String POST_PATH = "/post";
        public static final String POST_GET = "/{id}";
        public static final String POST_GET_ALL_PAGINATED = "/all";//TODO should allow to filter by favorited account and account posts
        public static final String POST_CREATE = "/{id}/create";
        public static final String POST_UPDATE = "/{id}/update";
        public static final String POST_DELETE = "/{id}/delete";
    }

    public static class ProfilePicture {
        public static final String PROFILE_PICTURE_PATH = "/profilepicture";
        public static final String PROFILE_PICTURE_GET = "/{id}";
        public static final String PROFILE_PICTURE_GET_ALL = "/all";
        public static final String PROFILE_PICTURE_ADD = "";
        public static final String PROFILE_PICTURE_CHANGE = "/{id}";
        public static final String PROFILE_PICTURE_DELETE = "/{id}";
    }

    public static class PostStatistic {
        public static final String POST_STATISTIC_PATH = "/poststatistic";
        public static final String POST_STATISTIC_GET = "/post/{id}";
        public static final String POST_STATISTIC_SET_VOTE = "/user/{userId}/post/{postId}/vote/{vote}";
        public static final String POST_STATISTIC_SET_SEEN = "/user/{userId}/post/{postId}/seen";
        public static final String POST_STATISTIC_SET_FAVORITE = "/user/{userId}/post/{postId}/favorite/{favorite}";
    }

    public static class Tag {
        public static final String TAG_PATH = "/tag";
        public static final String TAG_GET = "/{id}";
        public static final String TAG_GET_ALL_PAGINATED = "/all";
        public static final String TAG_POST = "/tag/{tag}";
        public static final String TAG_REMOVE = "/{id}";
        public static final String TAG_UPDATE = "/{id}/tag/{tag}";
    }

    public static class AccountPermission {
        public static final String ACCOUNT_PERMISSION_PATH = "/accountpermission";
        public static final String ACCOUNT_PERMISSION_GET = "/{id}";
        public static final String ACCOUNT_PERMISSION_GET_ALL_PAGINATED = "/all";
    }

    public static class Comment {
        public static final String COMMENT_PATH = "/comment";
        public static final String COMMENT_REPLY = "/{postId}/reply/{commentId}";
        public static final String COMMENT_REMOVE = "/{id}";
    }

    public static class CommentStatistic {
        public static final String COMMENT_STATISTIC_PATH = "/commentstatistic";
        public static final String POST_STATISTIC_GET = "/comment/{id}";
        public static final String POST_STATISTIC_SET_VOTE = "/user/{userId}/comment/{commentId}/vote/{vote}";
    }

    public static class Authentication {
        public static final String AUTHENTICATION_PATH = "/authentication";
        public static final String REFRESH_TOKEN = "/refresh";
    }

}