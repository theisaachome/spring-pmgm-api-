package com.highway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highway.domain.Project;
import com.highway.exceptions.ProjectIdException;
import com.highway.repos.ProjectRepos;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepos projectRepos;
	
	
	public Project	saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepos.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project Identifier duplicated"+project.getProjectIdentifier().toUpperCase());
		}
	}

}
