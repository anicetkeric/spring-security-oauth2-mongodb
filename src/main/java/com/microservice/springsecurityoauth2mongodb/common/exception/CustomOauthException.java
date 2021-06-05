/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 30/06/19 22:33.
 * @aek
 *
 * www.sudcontractors.com
 *
 */

package com.microservice.springsecurityoauth2mongodb.common.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Map;

/**
 * <h2>CustomOauthException</h2>
 *
 * @author aek
 * <p>
 * Description: Custom Oauth Exception class
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private static final long serialVersionUID = 1L;

    public CustomOauthException(String msg) {
        super(msg);
    }

    public CustomOauthException(String msg, Map<String, String> valueAdditionalInformation) {
        super(msg);
        if (valueAdditionalInformation != null) {
            for (Map.Entry<String, String> entry : valueAdditionalInformation.entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                this.addAdditionalInformation(key, add);
            }
        }

    }

}
