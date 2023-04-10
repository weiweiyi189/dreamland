package com.example.dreamland.controller;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.service.DreamCommentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class DreamCommentController {

    @Autowired
    DreamCommentService dreamCommentService;

    @GetMapping("getAll/{dreamId}")
    @JsonView(GetAllJsonView.class)
    public List<DreamComment> getAll(@PathVariable Long dreamId) {
        return this.dreamCommentService.getAll(dreamId);
    }

    @PostMapping("add")
    @JsonView(GetAllJsonView.class)
    public DreamComment add(@RequestBody DreamComment dreamComment) {
        return this.dreamCommentService.add(dreamComment);
    }



    private interface GetAllJsonView extends DreamComment.UserJsonView, DreamComment.DreamJsonView {
    }

}
