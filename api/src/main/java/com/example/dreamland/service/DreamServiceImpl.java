package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.repository.DreamRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DreamServiceImpl implements DreamService {

    private final DreamRepository dreamRepository;

    private final UserService userService;

    DreamServiceImpl(DreamRepository dreamRepository,
                     UserService userService) {
        this.dreamRepository = dreamRepository;
        this.userService = userService;
    }

    @Override
    public List<Dream> getAll() {
        return this.dreamRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public Dream add(Dream dream) {
        dream.setCreateUser(this.userService.getCurrentUser());
        return this.dreamRepository.save(dream);
    }

    @Override
    public Dream getById(Long id) {
        return this.dreamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关商品"));
    }
}
