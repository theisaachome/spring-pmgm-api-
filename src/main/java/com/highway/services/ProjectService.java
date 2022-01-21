package com.highway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highway.domain.Project;
import com.highway.exceptions.ProjectIdException;
import com.highway.repos.ProjectRepos;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepos projectRepos;
	
	public List<Project> getAllProjects(){
		return projectRepos.findAll();
	}
	public Project	saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepos.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project Identifier duplicated"+project.getProjectIdentifier().toUpperCase());
		}
	}
	
	public Project findByProjectIdentifier(String projectIdentifier) {
		Project project = this.projectRepos
				.findByProjectIdentifier(projectIdentifier.toUpperCase())
				.orElseThrow(()-> 
						new ProjectIdException("No Found with Project Identifier : "+projectIdentifier.toUpperCase()));

		return project;
	}

	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepos
				.findByProjectIdentifier(projectId.toUpperCase())
				.orElseThrow(()-> 
						new ProjectIdException("There is no Project with Identifier : "+projectId.toUpperCase()));

		projectRepos.delete(project);
	}
}
