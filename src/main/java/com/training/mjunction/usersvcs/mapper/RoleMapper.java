package com.training.mjunction.usersvcs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.training.mjunction.usersvcs.data.domain.Role;
import com.training.mjunction.usersvcs.webservice.resources.RoleResource;

@Mapper
public interface RoleMapper {

	Role toRole(RoleResource request);

	@Mappings({ @Mapping(target = "authority", source = "role.authority") })
	RoleResource toRoleResource(Role role);
}
