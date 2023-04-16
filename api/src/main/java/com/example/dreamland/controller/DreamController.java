package com.example.dreamland.controller;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.service.DreamService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dream")
public class DreamController {

    @Autowired
    DreamService dreamService;

    @GetMapping("getAll")
    @JsonView(GetAllJsonView.class)
    public List<Dream> getAll() {
        return this.dreamService.getAll();
    }

    @GetMapping("getAllByCurrentUser")
    @JsonView(GetAllJsonView.class)
    public List<Dream> getAllByCurrentUser() {
        return this.dreamService.getAllByCurrentUser();
    }

    @GetMapping("getAllByUserId/{id}")
    @JsonView(GetAllJsonView.class)
    public List<Dream> getAllByUserId(@PathVariable Long id) {
        return this.dreamService.getAllByUserId(id);
    }

    @GetMapping("getCollectDream")
    @JsonView(GetAllJsonView.class)
    public List<Dream> getCollectDream() {
        return this.dreamService.getCollectDreamByCurrentUser();
    }


    @PostMapping("add")
    @JsonView(GetByIdJsonView.class)
    public Dream add(@RequestBody Dream dream) {
        return this.dreamService.add(dream);
    }

    @GetMapping("getById/{id}")
    @JsonView(GetByIdJsonView.class)
    public Dream getById(@PathVariable Long id) {
        return this.dreamService.getById(id);
    }


    private interface GetAllJsonView extends Dream.UserJsonView {
    }

    private interface GetByIdJsonView extends GetAllJsonView {
    }
}
