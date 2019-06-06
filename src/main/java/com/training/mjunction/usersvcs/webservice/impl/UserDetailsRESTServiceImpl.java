package com.training.mjunction.usersvcs.webservice.impl;

import java.net.URI;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.data.domain.User;
import com.training.mjunction.usersvcs.mapper.UserMapper;
import com.training.mjunction.usersvcs.service.UserDetailsService;
import com.training.mjunction.usersvcs.webservice.UserDetailsRESTService;
import com.training.mjunction.usersvcs.webservice.resources.LinkResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class UserDetailsRESTServiceImpl implements UserDetailsRESTService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Validator validator;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Response createUser(final UserDetailsRequestResource userDetailsRequest, final UriInfo uriInfo) {

		final Set<ConstraintViolation<UserDetailsRequestResource>> violations = validator.validate(userDetailsRequest);

		for (final ConstraintViolation<UserDetailsRequestResource> constraintViolation : violations) {
			log.error(constraintViolation.getMessage());
		}

		final UserMapper mapper = Mappers.getMapper(UserMapper.class);

		final User user = mapper.toUser(userDetailsRequest);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		final User created = userDetailsService.save(user);

		final UserDetailsResponseResource response = mapper.toUserDetailsRequestResource(created);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUsername()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

	@Override
	public Response searchUsers(final String userName, final UriInfo uriInfo) {

		final User user = userDetailsService.loadUserByUsername(userName);

		if (null == user) {
			throw new IllegalArgumentException("Username not found.");
		}

		final UserMapper responseMapper = Mappers.getMapper(UserMapper.class);

		final UserDetailsResponseResource response = responseMapper.toUserDetailsRequestResource(user);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUsername()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

}
