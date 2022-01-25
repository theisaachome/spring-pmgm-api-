package com.highway.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.User;

@Repository
public interface UserRepos extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	Optional<User> findById(Long id);
}