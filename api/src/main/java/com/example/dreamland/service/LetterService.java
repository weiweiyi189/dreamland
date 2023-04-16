package com.example.dreamland.service;

import com.example.dreamland.entity.Letter;

import java.util.List;

public interface LetterService {
    List<Letter> getAll();

    List<Letter> getAllByOrderByCreateTimeDesc();

    Letter getTopNotShowed(Long id);

    Letter add(Letter letter);



    Letter getById(Long id);

    List<Letter> getAllByCreateUserId(Long id);

    List<Letter> getAllshowed(Long id);

    Letter modifyLetter(Letter letter);
}
