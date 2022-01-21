package com.highway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.ProjectTask;

@Repository
public interface TaskRepo extends JpaRepository<ProjectTask, Long>{

}
