package com.zappcomments.zappcomments.commentservice.api.config.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

public class StringToTSIDDeserializer extends JsonDeserializer<TSID> {

    @Override
    public TSID deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {

        return TSID.from(p.getText());
    }

}
