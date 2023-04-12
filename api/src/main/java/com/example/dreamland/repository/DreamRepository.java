package com.example.dreamland.repository;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.repository.specs.DreamCommentSpecs;
import com.example.dreamland.repository.specs.DreamSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DreamRepository extends CrudRepository<Dream, Long>, JpaSpecificationExecutor<Dream> {
    List<Dream> findAll();

    List<Dream> findAllByOrderByCreateTimeDesc();

    default List<Dream> findAllByUserIdAndCreamTimeDesc(Long userId) {
        Specification<Dream> specification = DreamSpecs.equalDreamIdAndCreatTimeDesc(userId);
        return this.findAll(specification);
    };
}
