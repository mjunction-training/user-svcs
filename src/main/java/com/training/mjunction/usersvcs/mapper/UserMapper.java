package com.training.mjunction.usersvcs.mapper;

import org.mapstruct.Mapper;

import com.training.mjunction.usersvcs.data.domain.User;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsResponseResource;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

	User toUser(UserDetailsRequestResource request);

	UserDetailsResponseResource toUserDetailsRequestResource(User user);
}
