package com.training.mjunction.usersvcs.mapper;

import org.mapstruct.Mapper;

import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

@Mapper
public interface UserDetailsMapper {
	UserDetailsResponseResource toResponse(UserDetailsRequestResource request);
}
