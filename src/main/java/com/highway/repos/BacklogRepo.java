package com.highway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.Backlog;

@Repository
public interface BacklogRepo extends JpaRepository<Backlog,Long> {

}
