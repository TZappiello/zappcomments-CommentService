package com.zappcomments.zappcomments.commentservice.domain.model;


import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PostId implements Serializable {

    private TSID value;

    public PostId(TSID value) {
        Objects.nonNull(value);
        this.value = value;
    }

    public PostId(Long value) {
        Objects.nonNull(value);
        this.value = TSID.from(value);
    }

    public PostId(String value) {
        Objects.nonNull(value);
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
