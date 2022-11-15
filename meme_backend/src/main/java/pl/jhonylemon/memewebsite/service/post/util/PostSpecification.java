package pl.jhonylemon.memewebsite.service.post.util;

import org.springframework.data.jpa.domain.Specification;
import pl.jhonylemon.memewebsite.dto.DateRangeDto;
import pl.jhonylemon.memewebsite.dto.LongRangeDto;
import pl.jhonylemon.memewebsite.entity.Post;

import javax.persistence.criteria.*;
import java.util.List;

public class PostSpecification {

    private PostSpecification() {}

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

    public static Specification<Post> hasTags(List<Long> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags != null) {
                return criteriaBuilder.in(root.join("tags").get("id")).value(tags);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfUpVotes(LongRangeDto upvotes) {
        return (root, query, criteriaBuilder) -> {
            if (upvotes != null) {

                Join j = root.join("postStatistics");

                query
                        .where(criteriaBuilder.equal(j.get("upVote"),true))
                        .having(criteriaBuilder.between(criteriaBuilder.count(j.get("upVote")),upvotes.getMin(), upvotes.getMax()))
                        .groupBy(root.get("id"));

                return query.getRestriction();

            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfDownVotes(LongRangeDto downvotes) {
        return (root, query, criteriaBuilder) -> {
            if (downvotes != null) {
                Join j = root.join("postStatistics");

                query
                        .where(criteriaBuilder.equal(j.get("downVote"),true))
                        .having(criteriaBuilder.between(criteriaBuilder.count(j.get("downVote")),downvotes.getMin(), downvotes.getMax()))
                        .groupBy(root.get("id"));

                return query.getRestriction();
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Post> hasNumberOfFavorites(LongRangeDto favorites) {
        return (root, query, criteriaBuilder) -> {
            if (favorites != null) {
                Join j = root.join("postStatistics");

                query
                        .where(criteriaBuilder.equal(j.get("favorite"),true))
                        .having(criteriaBuilder.between(criteriaBuilder.count(j.get("favorite")),favorites.getMin(), favorites.getMax()))
                        .groupBy(root.get("id"));

                return query.getRestriction();
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
