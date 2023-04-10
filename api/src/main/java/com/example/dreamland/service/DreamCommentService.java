package com.example.dreamland.service;

import com.example.dreamland.entity.DreamComment;

import java.util.List;

public interface DreamCommentService {

    List<DreamComment> getAll(Long dreamId);

    DreamComment add(DreamComment dream);

//    DreamComment getById(Long id);
}
