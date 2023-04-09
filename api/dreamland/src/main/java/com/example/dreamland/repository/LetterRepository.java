package com.example.dreamland.repository;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LetterRepository extends CrudRepository<Letter, Long>, JpaSpecificationExecutor<Letter> {
    List<Letter> findAll();

    List<Dream> findAllByOrderByCreateTimeDesc();
}
