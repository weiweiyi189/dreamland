package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.repository.DreamRepository;
import com.example.dreamland.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DreamServiceImpl implements DreamService {

    private final DreamRepository dreamRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    DreamServiceImpl(DreamRepository dreamRepository,
                     UserService userService,
                     UserRepository userRepository) {
        this.dreamRepository = dreamRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Dream> getAll() {
        return this.dreamRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public List<Dream> getAllByCurrentUser() {
        User user = this.userService.getCurrentUser();
        return this.dreamRepository.findAllByUserIdAndCreamTimeDesc(user.getId());
    }

    @Override
    public List<Dream> getCollectDreamByCurrentUser() {
        User user = this.userService.getCurrentUser();
        return user.getCollectDream();
    }

    @Override
    public Dream add(Dream dream) {
        User user = null;
        if (dream.getCreateUser() != null && dream.getCreateUser().getUsername().equals("匿名用户")) {
            user = this.userRepository.findByUsername("匿名用户");
        } else {
            user = this.userService.getCurrentUser();
        }
        dream.setCreateUser(user);
        return this.dreamRepository.save(dream);
    }

    @Override
    public Dream getById(Long id) {
        return this.dreamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关商品"));
    }
}
