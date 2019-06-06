package com.training.mjunction.usersvcs.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.mjunction.usersvcs.data.domain.OAuth2Role;

@Repository
public interface OAuth2RoleRepository extends JpaRepository<OAuth2Role, String> {
	Optional<OAuth2Role> findByAuthority(String authority);
}
