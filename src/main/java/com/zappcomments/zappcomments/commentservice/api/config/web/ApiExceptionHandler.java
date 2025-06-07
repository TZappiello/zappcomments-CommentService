package com.zappcomments.zappcomments.commentservice.api.config.web;

import com.zappcomments.zappcomments.commentservice.api.client.exception.CommentClientBadGatewayException;
import com.zappcomments.zappcomments.commentservice.api.client.exception.CommentNotApprovedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class
    })
    public ProblemDetail handle(IOException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);

        problemDetail.setTitle("Gateway Timeout");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(CommentClientBadGatewayException.class)
    public ProblemDetail handle(CommentClientBadGatewayException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);

        problemDetail.setTitle("Bad Gateway");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;
    }

    @ExceptionHandler(CommentNotApprovedException.class)
    public ProblemDetail handle(CommentNotApprovedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY );

        problemDetail.setTitle("Unprocessable Entity");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/unprocessable-entity"));

        return problemDetail;
    }

}
