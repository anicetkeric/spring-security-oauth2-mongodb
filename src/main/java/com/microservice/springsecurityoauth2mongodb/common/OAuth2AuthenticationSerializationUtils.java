package com.microservice.springsecurityoauth2mongodb.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.springframework.security.oauth2.common.util.SerializationUtils.deserialize;
import static org.springframework.security.oauth2.common.util.SerializationUtils.serialize;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2AuthenticationSerializationUtils {

    public static String serializer(OAuth2Authentication object) {
        byte[] bytes = serialize(object);
        return Base64.encodeBase64String(bytes);
    }

    public static OAuth2Authentication deserializer(String encodedObject) {
        byte[] bytes = Base64.decodeBase64(encodedObject);
        return (OAuth2Authentication) deserialize(bytes);
    }

}