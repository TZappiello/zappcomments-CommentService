package com.zappcomments.zappcomments.commentservice.api.controller;

import com.zappcomments.zappcomments.commentservice.api.model.CommentInput;
import com.zappcomments.zappcomments.commentservice.api.model.CommentOutput;
import com.zappcomments.zappcomments.commentservice.domain.service.CommentService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@RequestBody CommentInput input) {
        return commentService.create(input);
    }

    @GetMapping("{commentId}")
    public CommentOutput getOne(@PathVariable TSID commentId) {
        return commentService.getOne(commentId);
    }

    @GetMapping
    public Page<CommentOutput> getAll(@PageableDefault Pageable pageable) {
        return commentService.getAll(pageable);
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID commentId) {
        commentService.delete(commentId);
    }

}
