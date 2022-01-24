package com.highway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.User;

@Repository
public interface UserRepos extends JpaRepository<User, Long> {

}
