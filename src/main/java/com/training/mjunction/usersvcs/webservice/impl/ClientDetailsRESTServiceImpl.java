package com.training.mjunction.usersvcs.webservice.impl;

import java.net.URI;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.data.domain.OAuth2Client;
import com.training.mjunction.usersvcs.mapper.OAuth2ClientMapper;
import com.training.mjunction.usersvcs.service.OAuth2ClientDetailsService;
import com.training.mjunction.usersvcs.webservice.ClientDetailsRESTService;
import com.training.mjunction.usersvcs.webservice.resources.LinkResource;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsResponseResource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class ClientDetailsRESTServiceImpl implements ClientDetailsRESTService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Validator validator;

	@Autowired
	private OAuth2ClientDetailsService oAuth2ClientDetailsService;

	@Override
	public Response createClient(@NotNull @Valid final OAuth2ClientDetailsRequestResource clientDetailsRequest,
			final UriInfo uriInfo) {

		final Set<ConstraintViolation<OAuth2ClientDetailsRequestResource>> violations = validator
				.validate(clientDetailsRequest);

		for (final ConstraintViolation<OAuth2ClientDetailsRequestResource> constraintViolation : violations) {
			log.error(constraintViolation.getMessage());
		}

		final OAuth2ClientMapper mapper = Mappers.getMapper(OAuth2ClientMapper.class);

		final OAuth2Client client = mapper.toClient(clientDetailsRequest);

		client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));

		final OAuth2Client created = oAuth2ClientDetailsService.save(client);

		final OAuth2ClientDetailsResponseResource response = mapper.toClientDetailsRequestResource(created);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getClientId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

	@Override
	public Response getClient(@NotNull final String clientId, final UriInfo uriInfo) {

		final OAuth2Client client = oAuth2ClientDetailsService.loadClientByClientId(clientId);

		if (null == client) {
			throw new IllegalArgumentException("clientId not found.");
		}

		final OAuth2ClientMapper mapper = Mappers.getMapper(OAuth2ClientMapper.class);

		final OAuth2ClientDetailsResponseResource response = mapper.toClientDetailsRequestResource(client);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getClientId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

}
