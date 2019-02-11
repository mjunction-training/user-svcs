package com.training.mjunction.usersvcs.webservice.impl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.webservice.UserDetailsRESTService;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;

@Service
public class UserDetailsRESTServiceImpl implements UserDetailsRESTService {

	@Override
	public Response createUser(@NotNull @Valid final UserDetailsRequestResource userDetailsRequest, final UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateUser(@NotNull final Long userId, @NotNull @Valid final UserDetailsRequestResource userDetailsRequest,
			final UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteUser(@NotNull final Long userId, final UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUser(@NotNull final Long userId, final UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response searchUsers(final String userName, final String firstName, final String lastName, final String email, final String phone,
			final UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
