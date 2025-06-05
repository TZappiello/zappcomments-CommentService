package com.zappcomments.zappcomments.commentservice.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TSIDToLongJPAAttributeConverter implements AttributeConverter<TSID, Long> {

    @Override
    public Long convertToDatabaseColumn(TSID tsid) {
        return tsid != null ? tsid.toLong() : null;
    }

    @Override
    public TSID convertToEntityAttribute(Long value) {
        return value != null ? TSID.from(value) : null;
    }
}
