package com.example.dreamland.controller;

import com.example.dreamland.entity.Letter;
import com.example.dreamland.service.LetterService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("letter")
public class LetterController {

    @Autowired
    LetterService letterService;

    @GetMapping("getAll")
    @JsonView(GetAllJsonView.class)
    public List<Letter> getAll() {
        return this.letterService.getAll();
    }

    @GetMapping("getAllByOrderByCreateTimeDesc")
    @JsonView(GetAllByOrderByCreateTimeDescJsonView.class)
    public List<Letter> getAllByOrderByCreateTimeDesc() {
        return this.letterService.getAllByOrderByCreateTimeDesc();
    }

    @PostMapping("add")
    @JsonView(AddJsonView.class)
    public Letter add(@RequestBody Letter letter) {
        return this.letterService.add(letter);
    }

    @PostMapping("modifyLetter")
    @JsonView(AddJsonView.class)
    public Letter modifyLetter(@RequestBody Letter letter) {
        return this.letterService.modifyLetter(letter);
    }

    @GetMapping("getById/{id}")
    @JsonView(GetByIdJsonView.class)
    public Letter getById(@PathVariable Long id) {
        return this.letterService.getById(id);
    }

    @GetMapping("getAllByCreateUserId/{id}")
    @JsonView(GetByCreateUserIdJsonView.class)
    public List<Letter> getByCreateUserId(@PathVariable Long id){
        return this.letterService.getAllByCreateUserId(id);
    }

    @GetMapping("getTopNotShowed/{id}")
    @JsonView(GetTopByCreateUserIdNotJsonView.class)
    public Letter getTopByCreateUserIdNot(@PathVariable Long id){
        return this.letterService.getTopNotShowed(id);
    }

    @GetMapping("getAllshowed/{id}")
    @JsonView(GetAllshowed.class)
    public List<Letter> getAllshowed(@PathVariable Long id){
        List<Letter> letters=this.letterService.getAllshowed(id);
        return this.letterService.getAllshowed(id);
    }

    private interface GetAllJsonView extends Letter.UserJsonView {
    }

    private interface GetByIdJsonView extends GetAllJsonView {
    }

    private interface GetTopByCreateUserIdNotJsonView extends GetAllJsonView{

    }

    private interface GetByCreateUserIdJsonView extends GetAllJsonView{

    }
    private interface GetAllByOrderByCreateTimeDescJsonView extends GetAllJsonView{

    }
    private interface AddJsonView extends GetAllJsonView{

    }
    private interface GetAllshowed extends GetAllJsonView{

    }
}
