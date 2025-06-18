package com.zappcomments.zappcomments.commentservice.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostData {

    private UUID postId;
    private Integer wordCount;
    private Double calculatedValue;
}
