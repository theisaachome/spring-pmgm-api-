package com.highway.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.highway.domain.Project;

@Repository
public interface ProjectRepos  extends CrudRepository<Project,Long>{

	@Override
	Iterable<Project> findAllById(Iterable<Long> ids) ;
}
