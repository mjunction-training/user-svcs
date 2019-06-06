package com.training.mjunction.usersvcs.webservice.resources;

import java.util.Set;
import java.util.SortedSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Client details request resource")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "username", "password", "firstName", "lastName", "email", "phone", "authorities" })
public class OAuth2ClientDetailsRequestResource {
	@NotNull
	@Size(min = 1, max = 36)
	@Pattern(regexp = "^[a-zA-Z0-9-]*$")
	@ApiModelProperty("Client Id")
	private String clientId;
	@NotNull
	@Size(min = 1, max = 50)
	@ApiModelProperty("Client Password")
	private String clientSecret;
	@NotNull
	@Size(min = 1, max = 50)
	@ApiModelProperty("Additional Info")
	private String additionalInfo;
	@NotNull
	@ApiModelProperty("Access Token Validity In Seconds")
	private Integer accessTokenValiditySeconds;
	@NotNull
	@ApiModelProperty("Refresh Token Validity In Seconds")
	private Integer refreshTokenValiditySeconds;
	@ApiModelProperty("Scope")
	private Set<String> scope;
	@ApiModelProperty("Resource ids")
	private Set<String> resourceIds;
	@ApiModelProperty("Auto Approve Scope")
	private Set<String> autoApproveScope;
	@ApiModelProperty("Authorized Grant Types")
	private Set<String> authorizedGrantTypes;
	@ApiModelProperty("Authorized Grant Types")
	private Set<String> registeredRedirectUri;
	@ApiModelProperty("Authorities")
	private SortedSet<RoleDetailsResource> authorities;
}
