package com.microservice.springsecurityoauth2mongodb.service;

import com.microservice.springsecurityoauth2mongodb.document.OAuthClientDetails;
import com.microservice.springsecurityoauth2mongodb.repositories.support.OAuthClientDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OAuthClientDetailsService implements ClientDetailsService, ClientRegistrationService {

	@Autowired
    private final OAuthClientDetailsRepository clientDetailsRepository;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId){
		
		log.info("loadClientByClientId clientId => {}",clientId);
		
        OAuthClientDetails clientDetails = clientDetailsRepository.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new NoSuchClientException(String.format("Client with id %s not found.", clientId));
        }
        return clientDetails;
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails){
		log.info("clientDetails infos => {}",clientDetails);
		OAuthClientDetails clientDetailsExist = clientDetailsRepository.loadClientByClientId(clientDetails.getClientId());
		if(null == clientDetailsExist) {
			clientDetailsRepository.addClientDetails(clientDetails);
		}else {
			throw new ClientAlreadyExistsException(String.format("Client already exists with id %s ",clientDetails.getClientId()));
		}
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails){
		log.info("updateClientDetails => {}",clientDetails);
		long updateClientCount = clientDetailsRepository.updateClientDetails(clientDetails);
		log.info("updateClientCount => {}",updateClientCount);
		if(updateClientCount <= 0) {
            throw new NoSuchClientException(String.format("Client with id %s not found", clientDetails.getClientId()));
        }
	}

	@Override
	public void updateClientSecret(String clientId, String secret){
		log.info("updateClientSecret clientId => {} and secret => {}",clientId,secret);
		long updateClientSecretCount = clientDetailsRepository.updateClientSecret(clientId, secret);
		log.info("updateClientSecretCount => {}",updateClientSecretCount);
		if(updateClientSecretCount <= 0) {
			throw new NoSuchClientException(String.format("Client with id %s not found", clientId));
        }
	}

	@Override
	public void removeClientDetails(String clientId){
		log.info("removeClientDetails clientId => {}",clientId);
		long removeClientDetailsCount = clientDetailsRepository.removeClientDetails(clientId);
		log.info("removeClientDetailsCount => {}",removeClientDetailsCount);
		if(removeClientDetailsCount <= 0) {
            throw new NoSuchClientException(String.format("Client with id %s not found", clientId));
        }
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		List<ClientDetails> clientList = clientDetailsRepository.listClientDetails();
		log.info("listClientDetails total count => {}",clientList.size());
		return clientList;
	}	
}
