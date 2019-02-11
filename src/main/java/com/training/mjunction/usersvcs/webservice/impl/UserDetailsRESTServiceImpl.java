package com.training.mjunction.usersvcs.webservice.impl;

import java.net.URI;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.mapper.UserDetailsMapper;
import com.training.mjunction.usersvcs.webservice.UserDetailsRESTService;
import com.training.mjunction.usersvcs.webservice.resources.LinkResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserDetailsRESTServiceImpl implements UserDetailsRESTService {

	@Autowired
	private Validator validator;

	// TODO not required for database implementation
	private final Map<String, UserDetailsResponseResource> userMap = new ConcurrentHashMap<>();

	@Override
	public Response createUser(final UserDetailsRequestResource userDetailsRequest, final UriInfo uriInfo) {

		final Set<ConstraintViolation<UserDetailsRequestResource>> violations = validator.validate(userDetailsRequest);

		for (final ConstraintViolation<UserDetailsRequestResource> constraintViolation : violations) {
			log.error(constraintViolation.getMessage());
		}

		// TODO jpa unique constraint will take care of this
		if (userMap.containsKey(userDetailsRequest.getUsername())) {
			throw new IllegalArgumentException("Username alreday used.");
		}

		final UserDetailsMapper mapper = Mappers.getMapper(UserDetailsMapper.class);

		final UserDetailsResponseResource response = mapper.toResponse(userDetailsRequest);

		// TODO database generated
		response.setUserId(new Random().nextLong());

		userMap.put(response.getUsername(), response);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUserId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

	@Override
	public Response searchUsers(final String userName, final UriInfo uriInfo) {

		// TODO DB implementation required
		final UserDetailsResponseResource response = userMap.get(userName);

		if (null == response) {
			throw new IllegalArgumentException("Username not found.");
		}

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUserId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

}
