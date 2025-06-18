package com.zappcomments.zappcomments.commentservice.api.controller;

import com.zappcomments.zappcomments.commentservice.api.model.PostInput;
import com.zappcomments.zappcomments.commentservice.api.model.PostOutput;
import com.zappcomments.zappcomments.commentservice.domain.service.PostService;
import lombok.RequiredArgsConstructor;
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
}
