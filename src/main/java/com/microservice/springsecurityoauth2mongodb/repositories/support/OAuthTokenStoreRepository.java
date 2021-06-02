package com.microservice.springsecurityoauth2mongodb.repositories.support;

import com.microservice.springsecurityoauth2mongodb.document.OAuthAccessToken;
import com.microservice.springsecurityoauth2mongodb.document.OAuthRefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

public interface OAuthTokenStoreRepository {

	OAuth2Authentication readAuthentication(String token);
	
	void storeAccessToken(OAuthAccessToken accessToken);
	
	OAuthAccessToken readAccessToken(String tokenValue);
	
	long removeAccessToken(OAuth2AccessToken oAuth2AccessToken);
	
	void storeRefreshToken(OAuthRefreshToken refreshToken);
	
	OAuthRefreshToken readRefreshToken(String tokenValue);
	
	OAuthRefreshToken readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken);
	
	long removeRefreshToken(OAuth2RefreshToken refreshToken);
	
	long removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken);
	
	OAuthAccessToken getAccessToken(String authenticationId);
	
	Collection<OAuthAccessToken> findTokensByClientIdAndUserName(String clientId, String username);
	
	Collection<OAuthAccessToken> findTokensByClientId(String clientId);
}
