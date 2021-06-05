package com.microservice.springsecurityoauth2mongodb.service;

import com.microservice.springsecurityoauth2mongodb.common.OAuth2AuthenticationSerializationUtils;
import com.microservice.springsecurityoauth2mongodb.document.OAuthAccessToken;
import com.microservice.springsecurityoauth2mongodb.document.OAuthRefreshToken;
import com.microservice.springsecurityoauth2mongodb.repositories.support.OAuthTokenStoreRepository;
import com.microservice.springsecurityoauth2mongodb.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class OAuthTokenStoreService implements TokenStore {
	

	private final AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	
	@Autowired
	private OAuthTokenStoreRepository tokenStoreRepository;

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken accessToken) {
		log.info("readAuthentication accessToken => {}",accessToken);
		return readAuthentication(accessToken.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		log.info("readAuthentication token => {}",token);
		return tokenStoreRepository.readAuthentication(token);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		log.info("storeAccessToken accessToken => {}",accessToken);
		log.info("storeAccessToken authentication => {}",authentication);
		String refreshToken = null;
        if (accessToken.getRefreshToken() != null) {
            refreshToken = accessToken.getRefreshToken().getValue();
        }
        
        if (readAccessToken(accessToken.getValue()) != null) {
            this.removeAccessToken(accessToken);
        }
        
        OAuthAccessToken token = new OAuthAccessToken();
        
        token.setTokenId(SecurityUtils.extractTokenKey(accessToken.getValue()));
        token.setToken(accessToken);
        token.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        token.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        token.setClientId(authentication.getOAuth2Request().getClientId());
        token.setAuthentication(OAuth2AuthenticationSerializationUtils.serializer(authentication));
        token.setRefreshToken(SecurityUtils.extractTokenKey(refreshToken));
        tokenStoreRepository.storeAccessToken(token);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		log.info("readAccessToken tokenValue => {}",tokenValue);
		OAuthAccessToken accessToken = tokenStoreRepository.readAccessToken(tokenValue);
		return accessToken != null ? accessToken.getToken() : null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
		log.info("removeAccessToken oAuth2AccessToken => {}",oAuth2AccessToken);
		long removeCount = tokenStoreRepository.removeAccessToken(oAuth2AccessToken);
		log.info("removeAccessToken removeCount => {}",removeCount);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		log.info("storeRefreshToken refreshToken => {}",refreshToken);
		log.info("storeRefreshToken authentication => {}",authentication);
		
		OAuthRefreshToken refreshTokenInfo = new OAuthRefreshToken();
		refreshTokenInfo.setTokenId(SecurityUtils.extractTokenKey(refreshToken.getValue()));
		refreshTokenInfo.setToken(refreshToken);
		refreshTokenInfo.setAuthentication(OAuth2AuthenticationSerializationUtils.serializer(authentication));
		tokenStoreRepository.storeRefreshToken(refreshTokenInfo);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		log.info("readRefreshToken tokenValue => {}",tokenValue);
		OAuthRefreshToken refreshToken = tokenStoreRepository.readRefreshToken(tokenValue);
		return (refreshToken != null) ? refreshToken.getToken() : null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
		log.info("readAuthenticationForRefreshToken refreshToken => {}",refreshToken);
		OAuthRefreshToken refreshTokenDetails = tokenStoreRepository.readAuthenticationForRefreshToken(refreshToken);
		return (refreshTokenDetails != null) ? OAuth2AuthenticationSerializationUtils.deserializer(refreshTokenDetails.getAuthentication()) : null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		log.info("removeRefreshToken token => {}",token);
		long removeRefreshTokenCount =tokenStoreRepository.removeRefreshToken(token);
		log.info("removeRefreshToken removeRefreshTokenCount => {}",removeRefreshTokenCount);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		log.info("removeAccessTokenUsingRefreshToken refreshToken => {}",refreshToken);
		long removeAccessTokenUsingRefreshTokenCount =tokenStoreRepository.removeAccessTokenUsingRefreshToken(refreshToken);
		log.info("removeAccessTokenUsingRefreshToken remove count => {}",removeAccessTokenUsingRefreshTokenCount);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		log.info("getAccessToken authentication => {}",authentication);
		OAuth2AccessToken accessToken = null;
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        OAuthAccessToken accessTokenDetails = tokenStoreRepository.getAccessToken(authenticationId);
        if (accessTokenDetails != null) {
            accessToken = accessTokenDetails.getToken();
            if(accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
                this.removeAccessToken(accessToken);
                this.storeAccessToken(accessToken, authentication);
            }
        }
		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String username) {
		log.info("findTokensByClientIdAndUserName clientId => {} , username",clientId,username);
		Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        Collection<OAuthAccessToken> accessTokens = tokenStoreRepository.findTokensByClientIdAndUserName(clientId, username);
        for (OAuthAccessToken accessToken : accessTokens) {
            tokens.add(accessToken.getToken());
        }
        return tokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		log.info("findTokensByClientId clientId => {} ",clientId);
		Collection<OAuth2AccessToken> tokens = new ArrayList<>();
		Collection<OAuthAccessToken> accessTokens = tokenStoreRepository.findTokensByClientId(clientId);
		for (OAuthAccessToken accessToken : accessTokens) {
            tokens.add(accessToken.getToken());
        }
		return tokens;
	}

}
