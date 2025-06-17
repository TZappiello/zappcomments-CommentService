package com.zappcomments.zappcomments.commentservice.domain.service;

import com.zappcomments.zappcomments.commentservice.api.client.CommentClient;
import com.zappcomments.zappcomments.commentservice.api.client.exception.CommentNotApprovedException;
import com.zappcomments.zappcomments.commentservice.api.model.CommentInput;
import com.zappcomments.zappcomments.commentservice.api.model.CommentOutput;
import com.zappcomments.zappcomments.commentservice.api.model.ModerationInput;
import com.zappcomments.zappcomments.commentservice.api.model.ModerationOutput;
import com.zappcomments.zappcomments.commentservice.common.IdGenerator;
import com.zappcomments.zappcomments.commentservice.domain.model.Comment;
import com.zappcomments.zappcomments.commentservice.domain.model.CommentId;
import com.zappcomments.zappcomments.commentservice.domain.repository.CommentRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentClient commentClient;

    public CommentOutput create(CommentInput input) {
        log.info("Creating comment: {}", input);
        Comment comment = Comment.builder()
                .id(new CommentId(IdGenerator.generateTSID()))
                .text(input.getText())
                .author(input.getAuthor())
                .build();

        log.info("Comment created: {}", comment);
        ModerationOutput res = commentClient.moderateComment(
                ModerationInput.builder()
                        .text(comment.getText())
                        .commentId(comment.getId().getValue())
                        .build());

        if (!res.isApproved()) {
            log.warn("Comment not approved: {}. Reason: {}", comment.getText(), res.getReason());
            throw new CommentNotApprovedException("Comment not approved by moderation");
        }

        log.info("Comment approved: {}", comment.getText());
        comment = commentRepository.saveAndFlush(comment);

        log.info("Comment saved: {}", comment);
        return getBuild(comment);
    }

    public CommentOutput getOne(TSID commentId) {
        log.info("Fetching comment with ID: {}", commentId);
        Comment comment = getComment(commentId);
        log.info("Comment found: {}", comment);
        return getBuild(comment);
    }

    public Page<CommentOutput> getAll(Pageable pageable) {
        log.info("Fetching all comments with pagination: {}", pageable);
        Page<Comment> res = commentRepository.findAll(pageable);

        log.info("Comments fetched: {} items", res.getTotalElements());
        return res.map(CommentService::getBuild);
    }

    public void delete(TSID commentId) {
        log.info("Fetching comment for deletion: {}", commentId);
        Comment comment = getComment(commentId);
        log.info("Comment found for deletion: {}", comment);
        commentRepository.delete(comment);
        log.info("Comment deleted: {}", commentId);
    }


    private Comment getComment(TSID commentId) {
        return commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    private static CommentOutput getBuild(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().getValue())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}