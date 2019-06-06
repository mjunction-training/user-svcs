package com.training.mjunction.usersvcs.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.NotImplementedException;

import com.training.mjunction.usersvcs.webservice.resources.ErrorResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Refresh Test Service", tags = "Refresh Test Service v1.0")
@Path("/v1/refresh")
public interface RefreshTestRESTService {

	@GET
	@Path("/test")
	@Produces({ "application/json" })
	@ApiOperation(value = "refreshTest", notes = "This API is used to search an existing user resources", tags = {
			"search" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful.", response = UserDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. User for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response refreshTest() {
		throw new NotImplementedException("Method not implemented");
	}

}
