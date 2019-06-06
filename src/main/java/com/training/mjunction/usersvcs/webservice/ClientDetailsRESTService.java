package com.training.mjunction.usersvcs.webservice;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.NotImplementedException;

import com.training.mjunction.usersvcs.webservice.resources.ErrorResource;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsResponseResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "clientService", tags = "Client REST AP for CRUD Operations v1.0")
@Path("/v1/clients")
public interface ClientDetailsRESTService {

	@POST
	@Path("/")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "createClientr", notes = "This API is used to register a new OAuth2 client ", tags = {
			"create" })
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successful.", response = OAuth2ClientDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. Client for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response createClient(
			@NotNull @Valid @ApiParam final OAuth2ClientDetailsRequestResource clientDetailsRequest,
			@Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@GET
	@Path("/{clientId}")
	@Produces({ "application/json" })
	@ApiOperation(value = "getClient", notes = "This API is used to get an existing client resource", tags = { "get" })
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successful.", response = OAuth2ClientDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. Client for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response getClient(@NotNull @PathParam("clientId") @ApiParam final String clientId,
			@Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@GET
	@Path("/")
	@Produces({ "application/json" })
	@ApiOperation(value = "getAllClients", notes = "This API is used to get an existing client resource", tags = {
			"get" })
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successful.", response = OAuth2ClientDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. Client for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response getAllClients(@Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@PUT
	@Path("/{clientId}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "updateClient", notes = "This API is used to update an existing OAuth2 client", tags = {
			"update" })
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successful.", response = OAuth2ClientDetailsResponseResource.class),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. Client for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response updateClient(@NotNull @PathParam("clientId") @ApiParam final String clientId,
			@NotNull @Valid @ApiParam final OAuth2ClientDetailsRequestResource clientDetailsRequest,
			@Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

	@DELETE
	@Path("/{clientId}")
	@Consumes({ "application/json" })
	@ApiOperation(value = "delateClient", notes = "This API is used to delete an existing client resource", tags = {
			"delete" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Successful."),
			@ApiResponse(code = 400, message = "Bad request data", response = ErrorResource.class),
			@ApiResponse(code = 401, message = "Unauthorized (Lacking Credentials for Authorized Resource. As per RFP 7235 - WWW-Authenticate Header needs to be sent for 401 containing one challenge)", response = ErrorResource.class),
			@ApiResponse(code = 403, message = "Forbidden. API does not need to explicitly deal with this. Will be handled by APIM and OAuth Filter", response = ErrorResource.class),
			@ApiResponse(code = 404, message = "Not Found. Client for given id not found.", response = ErrorResource.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorResource.class) })
	default Response deleteClient(@NotNull @PathParam("clientId") @ApiParam final String clientId,
			@Context final UriInfo uriInfo) {
		throw new NotImplementedException("Method not implemented");
	}

}
