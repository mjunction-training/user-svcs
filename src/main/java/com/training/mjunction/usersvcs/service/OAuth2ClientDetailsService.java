package com.training.mjunction.usersvcs.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import com.training.mjunction.usersvcs.data.domain.OAuth2Client;

public interface OAuth2ClientDetailsService extends ClientDetailsService {

	@Override
	OAuth2Client loadClientByClientId(String clientId) throws ClientRegistrationException;

	OAuth2Client save(OAuth2Client client);

	void delete(String clientId);

}
