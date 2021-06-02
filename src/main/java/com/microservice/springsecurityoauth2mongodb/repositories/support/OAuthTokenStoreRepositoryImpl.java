package com.microservice.springsecurityoauth2mongodb.repositories.support;

import com.microservice.springsecurityoauth2mongodb.common.OAuth2AuthenticationSerializationUtils;
import com.microservice.springsecurityoauth2mongodb.common.OAuthFieldsConstant;
import com.microservice.springsecurityoauth2mongodb.document.OAuthAccessToken;
import com.microservice.springsecurityoauth2mongodb.document.OAuthRefreshToken;
import com.microservice.springsecurityoauth2mongodb.security.SecurityUtils;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class OAuthTokenStoreRepositoryImpl implements OAuthTokenStoreRepository {

	private final MongoTemplate mongoTemplate;

	public OAuthTokenStoreRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(token)));
        OAuthAccessToken accessToken = mongoTemplate.findOne(query, OAuthAccessToken.class);
        return (accessToken != null) ? OAuth2AuthenticationSerializationUtils.deserializer(accessToken.getAuthentication()) : null;
	}

	@Override
	public void storeAccessToken(OAuthAccessToken accessToken) {
		mongoTemplate.save(accessToken);
	}

	@Override
	public OAuthAccessToken readAccessToken(String tokenValue) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(tokenValue)));
        return mongoTemplate.findOne(query, OAuthAccessToken.class);
	}

	@Override
	public long removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(oAuth2AccessToken.getValue())));
        DeleteResult removeToken=mongoTemplate.remove(query, OAuthAccessToken.class);
		return removeToken.getDeletedCount();
	}

	@Override
	public void storeRefreshToken(OAuthRefreshToken refreshToken) {
		mongoTemplate.save(refreshToken);
	}

	@Override
	public OAuthRefreshToken readRefreshToken(String tokenValue) {
		 Query query = new Query();
	     query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(tokenValue)));
		return mongoTemplate.findOne(query, OAuthRefreshToken.class);
	}

	@Override
	public OAuthRefreshToken readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(refreshToken.getValue())));
		return mongoTemplate.findOne(query, OAuthRefreshToken.class);
	}

	@Override
	public long removeRefreshToken(OAuth2RefreshToken refreshToken) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.TOKEN_ID).is(SecurityUtils.extractTokenKey(refreshToken.getValue())));
        DeleteResult removeRefreshToken = mongoTemplate.remove(query, OAuthRefreshToken.class);
		return removeRefreshToken.getDeletedCount();
	}

	@Override
	public long removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.REFRESH_TOKEN).is(SecurityUtils.extractTokenKey(refreshToken.getValue())));
        DeleteResult removeAccessTokenUsingRefreshToken= mongoTemplate.remove(query, OAuthAccessToken.class);
		return removeAccessTokenUsingRefreshToken.getDeletedCount();
	}

	@Override
	public OAuthAccessToken getAccessToken(String authenticationId) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.AUTHENTICATION_ID).is(authenticationId));
		return mongoTemplate.findOne(query, OAuthAccessToken.class);
	}

	@Override
	public Collection<OAuthAccessToken> findTokensByClientIdAndUserName(String clientId, String username) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientId));
        query.addCriteria(Criteria.where(OAuthFieldsConstant.USERNAME).is(username));
		return mongoTemplate.find(query, OAuthAccessToken.class);
	}

	@Override
	public Collection<OAuthAccessToken> findTokensByClientId(String clientId) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientId));
		return mongoTemplate.find(query, OAuthAccessToken.class);
	}
}
