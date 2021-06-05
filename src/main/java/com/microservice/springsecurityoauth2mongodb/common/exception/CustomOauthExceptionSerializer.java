/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 30/06/19 22:34.
 * @aek
 *
 * www.sudcontractors.com
 *
 */

package com.microservice.springsecurityoauth2mongodb.common.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * <h2>CustomOauthExceptionSerializer</h2>
 *
 * @author aek
 * <p>
 * Description: Custom Oauth Exception Serializer Class
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    private static final long serialVersionUID = 1L;

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("data", null);
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("error", value.getAdditionalInformation()!= null ? String.valueOf(false) : String.valueOf(true));


        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
