package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;

import java.util.List;

public interface DreamService {
    List<Dream> getAllByCurrentUser();

    Dream add(Dream dream);

    Dream getById(Long id);
}
