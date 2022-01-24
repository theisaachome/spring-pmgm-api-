package com.highway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.highway.domain.User;
import com.highway.exceptions.UsernameAlreadyExistException;
import com.highway.repos.UserRepos;

@Service
public class UserService {
	
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private BCryptPasswordEncoder  bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			// make sure username unqiue
			newUser.setUsername(newUser.getUsername());
			
			return userRepos.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistException("username already exist");
		}
	}

}
