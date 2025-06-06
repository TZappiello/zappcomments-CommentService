package com.zappcomments.zappcomments.commentservice.api.controller;

import com.zappcomments.zappcomments.commentservice.api.model.CommentInput;
import com.zappcomments.zappcomments.commentservice.api.model.CommentOutput;
import com.zappcomments.zappcomments.commentservice.common.IdGenerator;
import com.zappcomments.zappcomments.commentservice.domain.model.Comment;
import com.zappcomments.zappcomments.commentservice.domain.model.CommentId;
import com.zappcomments.zappcomments.commentservice.domain.repository.CommentRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@RequestBody CommentInput input) {

        Comment comment = Comment.builder()
                .id(new CommentId(IdGenerator.generateTSID()))
                .text(input.getText())
                .author(input.getAuthor())
                .build();

        comment = commentRepository.saveAndFlush(comment);

        return CommentOutput.builder()
                .id(comment.getId().getValue())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();

    }

    @GetMapping("{commentId}")
    public CommentOutput getOne(@PathVariable TSID commentId) {

        Comment comment = commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return CommentOutput.builder()
                .id(comment.getId().getValue())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @GetMapping
    public Page<CommentOutput> getAll(@PageableDefault Pageable pageable) {
        Page<Comment> res = commentRepository.findAll(pageable);

        return res.map(comment -> CommentOutput.builder()
                .id(comment.getId().getValue())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build());
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID commentId) {
        Comment comment = commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        commentRepository.delete(comment);
    }
}
