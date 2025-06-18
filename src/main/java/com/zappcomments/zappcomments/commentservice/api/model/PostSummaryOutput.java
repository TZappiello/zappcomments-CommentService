package com.zappcomments.zappcomments.commentservice.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSummaryOutput {

    private String id;
    private String title;
    private String summary;
    private String author;

}

