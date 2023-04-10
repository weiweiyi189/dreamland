package com.example.dreamland.repository.specs;

import com.example.dreamland.entity.Dream;
import org.springframework.data.jpa.domain.Specification;

public class DreamSpecs {

    public static Specification<Dream> equalDreamIdAndCreatTimeDesc(Long userId) {
        if (userId != null) {
            return (root, criteriaQuery, criteriaBuilder) -> {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.equal(root.join("user").get("id"), userId);
            };
        } else {
            return Specification.where(null);
        }
    }


}
