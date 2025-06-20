package com.zappcomments.zappcomments.commentservice.api.controller;

import com.zappcomments.zappcomments.commentservice.api.model.PostInput;
import com.zappcomments.zappcomments.commentservice.api.model.PostOutput;
import com.zappcomments.zappcomments.commentservice.api.model.PostSummaryOutput;
import com.zappcomments.zappcomments.commentservice.domain.service.PostService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutput create(@RequestBody @Validated PostInput input) {
        return postService.createPost(input);
    }

    @GetMapping("{postId}")
    public PostOutput getOne(@PathVariable TSID postId) {
        return postService.getOne(postId);
    }

    @GetMapping
    public Page<PostSummaryOutput> getAll(@PageableDefault Pageable pageable) {
        return postService.getAll(pageable);
    }


    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID postId) {
        postService.delete(postId);
    }
}
