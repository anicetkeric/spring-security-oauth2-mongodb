/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 30/06/19 22:34.
 * @aek
 *
 * www.sudcontractors.com
 *
 */

package com.microservice.springsecurityoauth2mongodb.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * <h2>CustomWebResponseExceptionTranslator</h2>
 *
 * @author aek
 * <p>
 * Description: Custom Web Response Exception Translator Class
 */
@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new CustomOauthException(oAuth2Exception.getMessage()));

        }  else if (e instanceof AuthenticationException) {
            AuthenticationException authenticationException = (AuthenticationException) e;
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new CustomOauthException(authenticationException.getMessage()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomOauthException(e.getMessage()));
        }
    }
}
