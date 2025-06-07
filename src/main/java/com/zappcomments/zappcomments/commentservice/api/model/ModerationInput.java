package com.zappcomments.zappcomments.commentservice.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModerationInput {

    private String text;
    private TSID commentId;

}

