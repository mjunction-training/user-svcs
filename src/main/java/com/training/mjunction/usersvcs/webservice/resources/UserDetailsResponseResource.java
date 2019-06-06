package com.training.mjunction.usersvcs.webservice.resources;

import java.util.Map;
import java.util.Set;

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
@ApiModel("User details response resource")
public class UserDetailsResponseResource {
	@ApiModelProperty("User name")
	private String username;
	@ApiModelProperty("Password")
	private String password;
	@ApiModelProperty("First Name")
	private String firstName;
	@ApiModelProperty("Last Name")
	private String lastName;
	@ApiModelProperty("Email")
	private String email;
	@ApiModelProperty("Phone")
	private String phone;
	@ApiModelProperty("Roles")
	private Set<RoleDetailsResource> authorities;
	@ApiModelProperty("Is Account Not Expired?")
	private boolean accountNonExpired;
	@ApiModelProperty("Is Account Not Locked?")
	private boolean accountNonLocked;
	@ApiModelProperty("Is Credential Not Expired?")
	private boolean credentialsNonExpired;
	@ApiModelProperty("Is User Enabled?")
	private boolean enabled;
	@ApiModelProperty("Links to self and other resources")
	private Map<String, LinkResource> links;

}
