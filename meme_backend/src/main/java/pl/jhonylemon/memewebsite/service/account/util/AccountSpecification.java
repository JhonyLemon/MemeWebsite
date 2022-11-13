package pl.jhonylemon.memewebsite.service.account.util;

import org.springframework.data.jpa.domain.Specification;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;
import pl.jhonylemon.memewebsite.entity.Account;

public class AccountSpecification {

    private AccountSpecification() {}

    public static Specification<Account> hasCreationDate(DateRangeDto creationDate) {
        return (root, query, criteriaBuilder) -> {
            if (creationDate != null) {
                return criteriaBuilder.between(
                        root.join("account").get("creationDate"),
                        creationDate.getMin(),
                        creationDate.getMax()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasNumberOfPosts(LongRangeDto posts) {
        return (root, query, criteriaBuilder) -> {
            if (posts != null) {
                return criteriaBuilder.between(
                        criteriaBuilder.size(root.join("account").get("posts")),
                                posts.getMin().intValue(),
                                posts.getMax().intValue()
                        );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasNumberOfComments(LongRangeDto comments) {
        return (root, query, criteriaBuilder) -> {
            if (comments != null) {
                return criteriaBuilder.between(
                        criteriaBuilder.size(root.join("account").get("comments")),
                        comments.getMin().intValue(),
                        comments.getMax().intValue()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasNumberOfUpVotes(LongRangeDto upvotes) {
        return (root, query, criteriaBuilder) -> {
            if (upvotes != null) {
                return criteriaBuilder.between(
                        criteriaBuilder.size(root.join("account").get("comments")),
                        upvotes.getMin().intValue(),
                        upvotes.getMax().intValue()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasNumberOfDownVotes(LongRangeDto downvotes) {
        return (root, query, criteriaBuilder) -> {
            if (downvotes != null) {
                return criteriaBuilder.between(
                        criteriaBuilder.size(root.join("account").get("comments")),
                        downvotes.getMin().intValue(),
                        downvotes.getMax().intValue()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasAccountName(String accountName) {
        return (root, query, criteriaBuilder) -> {
            if (accountName != null) {
                return criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root
                                        .join("account")
                                        .get("name")), getStringInLikeOperationFormat(accountName));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasAccountEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder
                        .equal(root
                                .join("account")
                                .get("enabled"),enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasAccountBanned(Boolean banned) {
        return (root, query, criteriaBuilder) -> {
            if (banned != null) {
                return criteriaBuilder
                        .equal(root
                                .join("account")
                                .get("banned"),banned);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static String getStringInLikeOperationFormat(String string) {
        return String.format("%%%s%%", string.toLowerCase());
    }
}