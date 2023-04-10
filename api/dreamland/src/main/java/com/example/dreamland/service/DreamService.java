package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;

import java.util.List;

public interface DreamService {
    List<Dream> getAllByUserId(Long userId);

    Dream add(Dream dream);

    Dream getById(Long id);
}
