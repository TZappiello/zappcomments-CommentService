package com.zappcomments.zappcomments.commentservice.api.client.impl;

import com.zappcomments.zappcomments.commentservice.api.client.CommentClient;
import com.zappcomments.zappcomments.commentservice.api.client.RestClientFactory;
import com.zappcomments.zappcomments.commentservice.api.model.ModerationInput;
import com.zappcomments.zappcomments.commentservice.api.model.ModerationOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CommentClientImpl implements CommentClient {

    private final RestClient restClient;

    public CommentClientImpl(RestClientFactory factory) {
        this.restClient = factory.restClient();
    }


    @Override
    public ModerationOutput moderateComment(ModerationInput input) {
        return restClient.post()
                .uri("/api/moderate")
                .body(input)
                .retrieve()
                .body(ModerationOutput.class);

    }
}
