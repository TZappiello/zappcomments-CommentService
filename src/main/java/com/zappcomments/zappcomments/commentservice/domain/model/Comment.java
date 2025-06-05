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
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private CommentId id;
    private String text;
    private String author;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
