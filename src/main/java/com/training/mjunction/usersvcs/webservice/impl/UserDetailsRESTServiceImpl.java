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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.data.domain.User;
import com.training.mjunction.usersvcs.data.repository.UserRepository;
import com.training.mjunction.usersvcs.mapper.UserMapper;
import com.training.mjunction.usersvcs.webservice.UserDetailsRESTService;
import com.training.mjunction.usersvcs.webservice.resources.LinkResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class UserDetailsRESTServiceImpl implements UserDetailsRESTService {

	@Autowired
	private Validator validator;

	@Autowired
	private UserRepository userRepository;

	@Value("${test.refresh.msg:Hi!}")
	private String mgs;

	@Override
	public Response refreshTest() {
		return Response.ok().entity("{\"refreshed\":\"" + mgs + "\"}").build();
	}

	@Override
	public Response createUser(final UserDetailsRequestResource userDetailsRequest, final UriInfo uriInfo) {

		final Set<ConstraintViolation<UserDetailsRequestResource>> violations = validator.validate(userDetailsRequest);

		for (final ConstraintViolation<UserDetailsRequestResource> constraintViolation : violations) {
			log.error(constraintViolation.getMessage());
		}

		final UserMapper mapper = Mappers.getMapper(UserMapper.class);

		final User user = mapper.toUser(userDetailsRequest);

		userRepository.save(user);

		final UserDetailsResponseResource response = mapper.toUserDetailsRequestResource(user);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUserId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

	@Override
	public Response searchUsers(final String userName, final UriInfo uriInfo) {

		final User user = userRepository.findByUsername(userName);

		if (null == user) {
			throw new IllegalArgumentException("Username not found.");
		}

		final UserMapper responseMapper = Mappers.getMapper(UserMapper.class);

		final UserDetailsResponseResource response = responseMapper.toUserDetailsRequestResource(user);

		final URI location = uriInfo.getAbsolutePathBuilder().path("/" + response.getUserId()).build();

		response.getLinks().put("self", new LinkResource(location.toString()));

		return Response.ok().location(location).entity(response).build();
	}

}
