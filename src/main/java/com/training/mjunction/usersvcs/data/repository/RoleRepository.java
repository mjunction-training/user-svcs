package com.training.mjunction.usersvcs.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.mjunction.usersvcs.data.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByAuthority(String authority);
}
