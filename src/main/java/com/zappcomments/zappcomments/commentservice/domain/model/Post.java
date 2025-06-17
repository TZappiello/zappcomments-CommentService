package com.zappcomments.zappcomments.commentservice.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id",
            columnDefinition = "BIGINT"))
    private PostId id;

    private String title;
    private String body;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;
}
