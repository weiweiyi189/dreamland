package com.example.dreamland.repository.specs;

import com.example.dreamland.entity.DreamComment;
import org.springframework.data.jpa.domain.Specification;

public class DreamCommentSpecs {

    public static Specification<DreamComment> equalDreamIdAndCreatTimeDesc(Long dreamId) {
        if (dreamId != null) {
            return (root, criteriaQuery, criteriaBuilder) -> {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.equal(root.join("dream").get("id"), dreamId);
            };
        } else {
            return Specification.where(null);
        }
    }


}
