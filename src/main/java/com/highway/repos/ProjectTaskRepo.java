package com.highway.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.ProjectTask;

@Repository
public interface ProjectTaskRepo extends JpaRepository<ProjectTask, Long>{

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	Optional<ProjectTask> findByProjectSequence(String sequence);
}
