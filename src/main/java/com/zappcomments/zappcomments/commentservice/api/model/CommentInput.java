package com.zappcomments.zappcomments.commentservice.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentInput {

    private String text;
    private String author;

}

