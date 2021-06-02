package com.microservice.springsecurityoauth2mongodb.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthFieldsConstant {


    public static final String TOKEN_ID = "tokenId";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String AUTHENTICATION_ID = "authenticationId";
    public static final String CLIENT_ID = "clientId";
    public static final String USERNAME = "username";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String SECRET_REQUIRED = "secretRequired";
    public static final String RESOURCE_IDS = "resourceIds";
    public static final String SCOPED = "scoped";
    public static final String SCOPE = "scope";
    public static final String AUTHORIZED_GRANT_TYPES = "authorizedGrantTypes";
    public static final String REGISTERED_REDIRECT_URI = "registeredRedirectUri";
    public static final String AUTHORITIES = "authorities";
    public static final String ACCESS_TOKEN_VALIDITY_SECONDS = "accessTokenValiditySeconds";
    public static final String REFRESH_TOKEN_VALIDITY_SECONDS = "refreshTokenValiditySeconds";
    public static final String AUTO_APPROVE = "autoApprove";
    public static final String ADDITIONAL_INFORMATION = "additionalInformation";

}
