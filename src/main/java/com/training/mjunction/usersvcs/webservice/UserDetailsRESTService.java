package com.training.mjunction.usersvcs.webservice;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.NotImplementedException;

import com.training.mjunction.usersvcs.webservice.resources.ErrorResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "userService", tags = "User REST AP for CRUD Operations v1.0")
@Path("/v1/users")
public interface UserDetailsRESTService {

	@POST
	@Path("/")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "createUser", notes = "This API is used to create a new user resource", tags = { "create" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful.", response = UserDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response createUser(@NotNull @Valid @ApiParam final UserDetailsRequestResource userDetailsRequest, @Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@PUT
	@Path("/{userId}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "updateUser", notes = "This API is used to update an existing user resource", tags = { "update" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful.", response = UserDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response updateUser(@NotNull @PathParam("userId") @ApiParam final Long userId,
			@NotNull @Valid @ApiParam final UserDetailsRequestResource userDetailsRequest, @Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@DELETE
	@Path("/{userId}")
	@Consumes({ "application/json" })
	@ApiOperation(value = "delateUser", notes = "This API is used to delete an existing user resource", tags = { "delete" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful."),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response deleteUser(@NotNull @PathParam("userId") @ApiParam final Long userId, @Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@GET
	@Path("/{userId}")
	@Produces({ "application/json" })
	@ApiOperation(value = "getUser", notes = "This API is used to get an existing user resource", tags = { "get" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful.", response = UserDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response getUser(@NotNull @PathParam("userId") @ApiParam final Long userId, @Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@GET
	@Path("/search")
	@Produces({ "application/json" })
	@ApiOperation(value = "searchUser", notes = "This API is used to search an existing user resources", tags = { "search" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful.", response = UserDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response searchUsers(@MatrixParam("userName") @QueryParam("userName") @ApiParam final String userName, @Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

}
