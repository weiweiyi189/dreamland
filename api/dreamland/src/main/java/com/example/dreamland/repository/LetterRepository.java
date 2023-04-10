package com.example.dreamland.repository;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends CrudRepository<Letter, Long>, JpaSpecificationExecutor<Letter> {
    List<Letter> findAll();

    List<Letter> findAllByOrderByCreateTimeDesc();

    @Query("select l from Letter l where l in(select ul from User u join u.letters ul where u.id=:id)and l.createUser.id<>:id order by l.createTime desc")
    List<Letter> findAllShowed(@Param("id")Long id);

    @Query("select l from Letter l where l not in(select ul from User u join u.letters ul where u.id=:id)and l.createUser.id<>:id order by l.createTime desc")
    List<Letter> findAllNotShowed(@Param("id")Long id);

    @Query("select l from Letter l where l.createUser.id=:id order by l.createTime desc")
    List<Letter> findAllByCreateUserId(@Param("id") Long id);

}
