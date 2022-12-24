package pl.jhonylemon.memewebsite.service.tag.util;

import org.springframework.data.jpa.domain.Specification;
import pl.jhonylemon.memewebsite.entity.Tag;

public class TagSpecification {

    private TagSpecification() {
    }

    public static Specification<Tag> hasTagLike(String tag) {
        return (root, query, criteriaBuilder) -> {
            if (tag != null) {
                return criteriaBuilder
                        .like(
                                criteriaBuilder.lower(root.get("tag")),
                                getStringInLikeOperationFormat(tag)
                        );
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static String getStringInLikeOperationFormat(String string) {
        return "%" + string.toLowerCase() + "%";
    }
}
