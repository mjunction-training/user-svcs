package com.training.mjunction.usersvcs.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.training.mjunction.usersvcs.data.domain.User;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	@Override
	User loadUserByUsername(String username) throws UsernameNotFoundException;

	User save(User user);

	void delate(String username);

}
