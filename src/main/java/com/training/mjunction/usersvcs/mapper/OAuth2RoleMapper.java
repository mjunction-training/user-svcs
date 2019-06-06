package com.training.mjunction.usersvcs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.training.mjunction.usersvcs.data.domain.OAuth2Role;
import com.training.mjunction.usersvcs.data.domain.Role;
import com.training.mjunction.usersvcs.webservice.resources.RoleDetailsResource;

@Mapper
public interface OAuth2RoleMapper {

	Role toRole(RoleDetailsResource request);

	@Mappings({ @Mapping(target = "authority", source = "role.authority") })
	RoleDetailsResource toRoleResource(OAuth2Role role);
}
