package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.repository.DreamCommentRepository;
import com.example.dreamland.repository.DreamRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DreamCommentServiceImpl implements DreamCommentService {


    private final DreamCommentRepository dreamCommentRepository;

    private final DreamRepository dreamRepository;

    private final UserService userService;

    DreamCommentServiceImpl(DreamCommentRepository dreamCommentRepository,
                            DreamRepository dreamRepository,
                            UserService userService) {
        this.dreamCommentRepository = dreamCommentRepository;
        this.dreamRepository = dreamRepository;
        this.userService = userService;
    }
    @Override
    public List<DreamComment> getAll(Long dreamId) {
        return this.dreamCommentRepository.findAllByDreamIdAndCreamTimeDesc(dreamId);
    }

    @Override
    public DreamComment add(DreamComment dreamComment) {
        Dream dream = this.dreamRepository.findById(dreamComment.getDream().getId()).orElseThrow(() -> new EntityNotFoundException("找不到相关梦境"));
        dreamComment.setDream(dream);
        dreamComment.setCreateUser(this.userService.getCurrentUser());
        return this.dreamCommentRepository.save(dreamComment);
    }

}
