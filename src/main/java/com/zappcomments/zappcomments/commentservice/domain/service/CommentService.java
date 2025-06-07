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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final CommentClient commentClient;

    public CommentOutput create(CommentInput input) {
        logger.info("Creating comment: {}", input);
        Comment comment = Comment.builder()
                .id(new CommentId(IdGenerator.generateTSID()))
                .text(input.getText())
                .author(input.getAuthor())
                .build();

        logger.info("Comment created: {}", comment);
        ModerationOutput res = commentClient.moderateComment(
                ModerationInput.builder()
                        .text(comment.getText())
                        .commentId(comment.getId().getValue())
                        .build());

        if (!res.isApproved()) {
            logger.warn("Comment not approved: {}. Reason: {}", comment.getText(), res.getReason());
            throw new CommentNotApprovedException("Comment not approved by moderation");
        }

        logger.info("Comment approved: {}", comment.getText());
        comment = commentRepository.saveAndFlush(comment);

        logger.info("Comment saved: {}", comment);
        return getBuild(comment);
    }

    public CommentOutput getOne(TSID commentId) {
        logger.info("Fetching comment with ID: {}", commentId);
        Comment comment = getComment(commentId);
        logger.info("Comment found: {}", comment);
        return getBuild(comment);
    }

    public Page<CommentOutput> getAll(Pageable pageable) {
        logger.info("Fetching all comments with pagination: {}", pageable);
        Page<Comment> res = commentRepository.findAll(pageable);

        logger.info("Comments fetched: {} items", res.getTotalElements());
        return res.map(CommentService::getBuild);
    }

    public void delete(TSID commentId) {
        logger.info("Fetching comment for deletion: {}", commentId);
        Comment comment = getComment(commentId);
        logger.info("Comment found for deletion: {}", comment);
        commentRepository.delete(comment);
        logger.info("Comment deleted: {}", commentId);
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