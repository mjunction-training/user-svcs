package com.training.mjunction.usersvcs.mapper;

import org.mapstruct.Mapper;

import com.training.mjunction.usersvcs.data.domain.OAuth2Client;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsRequestResource;
import com.training.mjunction.usersvcs.webservice.resources.OAuth2ClientDetailsResponseResource;

@Mapper(uses = OAuth2RoleMapper.class)
public interface OAuth2ClientMapper {

	OAuth2Client toClient(OAuth2ClientDetailsRequestResource request);

	OAuth2ClientDetailsResponseResource toClientDetailsRequestResource(OAuth2Client user);
}
