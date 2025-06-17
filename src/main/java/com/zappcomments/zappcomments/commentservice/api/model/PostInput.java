package com.zappcomments.zappcomments.commentservice.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostInput {

    private String title;
    private String body;
    private String author;
}
