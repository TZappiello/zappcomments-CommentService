package com.zappcomments.zappcomments.commentservice.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentOutput {

    private TSID id;
    private String text;
    private String author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime createdAt;


}

