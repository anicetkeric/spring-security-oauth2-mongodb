package com.microservice.springsecurityoauth2mongodb.repositories.support;

import com.microservice.springsecurityoauth2mongodb.common.OAuthFieldsConstant;
import com.microservice.springsecurityoauth2mongodb.document.OAuthClientDetails;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OAuthClientDetailsRepositoryImpl implements OAuthClientDetailsRepository {

	private final MongoTemplate mongoTemplate;

	public OAuthClientDetailsRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public OAuthClientDetails loadClientByClientId(String clientId) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientId));
		return mongoTemplate.findOne(query, OAuthClientDetails.class);
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) {
		mongoTemplate.save(clientDetails);
	}

	@Override
	public long updateClientDetails(ClientDetails clientDetails) {
		
        Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientDetails.getClientId()));

        Update update = new Update();
        update.set(OAuthFieldsConstant.RESOURCE_IDS, clientDetails.getResourceIds());
        update.set(OAuthFieldsConstant.SCOPE, clientDetails.getScope());
        update.set(OAuthFieldsConstant.AUTHORIZED_GRANT_TYPES, clientDetails.getAuthorizedGrantTypes());
        update.set(OAuthFieldsConstant.REGISTERED_REDIRECT_URI, clientDetails.getRegisteredRedirectUri());
        update.set(OAuthFieldsConstant.AUTHORITIES, clientDetails.getAuthorities());
        update.set(OAuthFieldsConstant.ACCESS_TOKEN_VALIDITY_SECONDS, clientDetails.getAccessTokenValiditySeconds());
        update.set(OAuthFieldsConstant.REFRESH_TOKEN_VALIDITY_SECONDS, clientDetails.getRefreshTokenValiditySeconds());
        update.set(OAuthFieldsConstant.ADDITIONAL_INFORMATION, clientDetails.getAdditionalInformation());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, OAuthClientDetails.class);
		
		return updateResult.getModifiedCount();
	}

	@Override
	public long updateClientSecret(String clientId, String clientSecret) {
		
        Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientId));

        Update update = new Update();
        update.set(OAuthFieldsConstant.CLIENT_SECRET,clientSecret);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, OAuthClientDetails.class);
        return updateResult.getModifiedCount();
	}

	@Override
	public long removeClientDetails(String clientId) {
		Query query = new Query();
        query.addCriteria(Criteria.where(OAuthFieldsConstant.CLIENT_ID).is(clientId));
        DeleteResult deleteResult = mongoTemplate.remove(query, OAuthClientDetails.class);
		return deleteResult.getDeletedCount();
	}

	@Override
	public List<ClientDetails> listClientDetails() {
        List<OAuthClientDetails> details = mongoTemplate.findAll(OAuthClientDetails.class);
        return new ArrayList<>(details);
	}
}
