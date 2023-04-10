package com.example.dreamland.repository;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.repository.specs.DreamCommentSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DreamCommentRepository extends CrudRepository<DreamComment, Long>, JpaSpecificationExecutor<DreamComment>  {

    default List<DreamComment> findAllByDreamIdAndCreamTimeDesc(Long dreamId) {
        Specification<DreamComment> specification = DreamCommentSpecs.equalDreamIdAndCreatTimeDesc(dreamId);
        return this.findAll(specification);
    };
}
