package com.training.mjunction.usersvcs.webservice.resources;

import java.util.Set;

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
@ApiModel("User details request resource")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "username", "password", "firstName", "latstName", "email", "phone", "authorities" })
public class UserDetailsRequestResource {
	@NotNull
	@Size(min = 1, max = 36)
	@Pattern(regexp = "^[a-zA-Z0-9-]*$")
	@ApiModelProperty("User name")
	private String username;
	@NotNull
	@Size(min = 1, max = 50)
	@ApiModelProperty("Password")
	private String password;
	@NotNull
	@Size(min = 1, max = 50)
	@ApiModelProperty("First Name")
	private String firstName;
	@NotNull
	@Size(min = 1, max = 50)
	@ApiModelProperty("Last Name")
	private String latstName;
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
	@ApiModelProperty("Email")
	private String email;
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "{invalid.phonenumber}")
	@ApiModelProperty("Phone")
	private String phone;
	@NotNull
	@ApiModelProperty("Roles")
	private Set<RoleResource> authorities;
}
