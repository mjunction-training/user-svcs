package com.training.mjunction.usersvcs.webservice.resources;

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
@ApiModel("Link resource")
public class LinkResource {
	@ApiModelProperty(value = "A link to a resource.")
	private String href;
}