package com.training.mjunction.usersvcs.webservice.resources;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("User role resource")
public class RoleDetailsResource {
	@NonNull
	@ApiModelProperty("User role")
	@Size(min = 1, max = 50)
	private String authority;
}
