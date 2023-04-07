package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;

import java.util.List;

public interface DreamService {
    List<Dream> getAll();

    Dream add(Dream goods);

    Dream getById(Long id);
}