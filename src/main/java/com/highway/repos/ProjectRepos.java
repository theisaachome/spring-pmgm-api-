package com.highway.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.Project;

@Repository
public interface ProjectRepos  extends JpaRepository<Project,Long>{
	
	Optional<Project>  findByProjectIdentifier(String projectIdentifier);
	List<Project> findByProjectLeader(String projectLeader);
}
