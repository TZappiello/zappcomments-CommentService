package com.zappcomments.zappcomments.commentservice.api.client.exception;

public class CommentNotApprovedException extends RuntimeException {

    public CommentNotApprovedException(String message) {
        super(message);
    }

    public CommentNotApprovedException(String message, Throwable cause) {
        super(message, cause);
    }
}
