package com.highway.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.highway.domain.User;
import com.highway.repos.UserRepos;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepos userRepos;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepos.findByUsername(username).orElseThrow(()->
		new UsernameNotFoundException("User not found with "));
		return user;
	}

	@Transactional
	public User loadUserById(Long id) {
		return userRepos.findById(id).orElseThrow(()->
		new UsernameNotFoundException("User not found with "));
	}
}
