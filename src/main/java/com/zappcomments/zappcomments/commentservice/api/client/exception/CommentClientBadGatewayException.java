package com.zappcomments.zappcomments.commentservice.api.client.exception;

public class CommentClientBadGatewayException extends RuntimeException {

    public CommentClientBadGatewayException(String message) {
        super(message);
    }

    public CommentClientBadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
