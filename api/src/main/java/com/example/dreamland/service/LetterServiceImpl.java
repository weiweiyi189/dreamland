package com.example.dreamland.service;

import com.example.dreamland.entity.Letter;
import com.example.dreamland.repository.DreamRepository;
import com.example.dreamland.repository.LetterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService{

    private final LetterRepository letterRepository;

    private final UserService userService;

    LetterServiceImpl(LetterRepository letterRepository,
                     UserService userService) {
        this.letterRepository = letterRepository;
        this.userService = userService;
    }

    @Override
    public List<Letter> getAll() {
        return this.letterRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public List<Letter> getAllByOrderByCreateTimeDesc() {
        return this.letterRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public Letter getTopNotShowed(Long id) {
        List<Letter> letterList=this.letterRepository.findAllNotShowed(id);
        if(letterList.isEmpty()){
            Letter letter=new Letter();
            letter.setTitle("NULL");
            letter.setContent("漂流瓶已经被捞干净啦!!\n\n请稍后再来吧.");
            return letter;
        }
        Letter letter=letterList.get(0);
        userService.addUserLetter(letter);
        return letter;
    }

    @Override
    public Letter add(Letter letter) {
        letter.setCreateUser(this.userService.getCurrentUser());
        return this.letterRepository.save(letter);
    }

    @Override
    public Letter getById(Long id) {
        return this.letterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关商品"));
    }

    @Override
    public List<Letter> getAllByCreateUserId(Long id) {
        return this.letterRepository.findAllByCreateUserId(id);
    }

    @Override
    public List<Letter> getAllshowed(Long id) {
        return this.letterRepository.findAllShowed(id);
    }


}
