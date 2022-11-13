package pl.jhonylemon.memewebsite.service.post.util;

import org.springframework.data.jpa.domain.Specification;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;
import pl.jhonylemon.memewebsite.entity.Post;

import javax.persistence.criteria.CriteriaQuery;

public class PostSpecification {

    private PostSpecification() {}

//    private LongRangeDto comments;
//    private LongRangeDto upVotes;
//    private LongRangeDto downVotes;
//    private LongRangeDto favorites;

    public static Specification<Post> hasCreationDate(DateRangeDto creationDate) {
        return (root, query, criteriaBuilder) -> {
            if (creationDate != null) {
                return criteriaBuilder.between(
                        root.get("creationDate"),
                        creationDate.getMin(),
                        creationDate.getMax()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfComments(LongRangeDto comments) {
        return (root, query, criteriaBuilder) -> {
            if (comments != null) {
                return criteriaBuilder.between(
                        criteriaBuilder.size(root.get("comments")),
                        comments.getMin().intValue(),
                        comments.getMax().intValue()
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfUpVotes(LongRangeDto upvotes) {
        return (root, query, criteriaBuilder) -> {
            if (upvotes != null) {



                return criteriaBuilder.and(
                        criteriaBuilder
                                .lessThanOrEqualTo(
                                        criteriaBuilder.count(
                                                criteriaBuilder.equal(root.joinList("postStatistics").get("vote"),true)
                                        ),
                                        upvotes.getMin()),
                        criteriaBuilder
                                .greaterThanOrEqualTo(
                                        criteriaBuilder.count(
                                                criteriaBuilder.equal(root.joinList("postStatistics").get("vote"),true)
                                        ),
                                        upvotes.getMax())
                );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfDownVotes(LongRangeDto downvotes) {
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

    public static Specification<Post> hasNumberOfFavorites(LongRangeDto downvotes) {
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

    public static Specification<Post> isPublic() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("visible"),true);
    }

    public static Specification<Post> hasTitleLike(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title != null) {
                return criteriaBuilder
                        .like(root.get("title"), getStringInLikeOperationFormat(title));
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static String getStringInLikeOperationFormat(String string) {
        return String.format("%%%s%%", string.toLowerCase());
    }
}
