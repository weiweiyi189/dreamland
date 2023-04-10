package com.example.dreamland.repository;

import com.example.dreamland.entity.Dream;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DreamRepository extends CrudRepository<Dream, Long>, JpaSpecificationExecutor<Dream> {
    List<Dream> findAll();


    List<Dream> findAllByOrderByCreateTimeDesc();
}
