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
        return this.dreamService.getAllByCurrentUser();
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
