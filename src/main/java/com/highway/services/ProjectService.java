package com.highway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.highway.domain.Backlog;
import com.highway.domain.Project;
import com.highway.domain.User;
import com.highway.exceptions.ProjectIdException;
import com.highway.exceptions.ProjectNotFoundException;
import com.highway.repos.BacklogRepo;
import com.highway.repos.ProjectRepos;
import com.highway.repos.UserRepos;

@Service
public class ProjectService {

	@Autowired
	private UserRepos userRepos;
	@Autowired
	private ProjectRepos projectRepos;
	@Autowired
	private BacklogRepo backlogRepo;

	public List<Project> getAllProjects(String username) {
		return projectRepos.findByProjectLeader(username);
	}
	public List<Project> getAllProjects() {
		return projectRepos.findAll();
	}


	public Project saveOrUpdateProject(Project project,String name) {
		//check project owner
 		if(project.getId() !=null) {
			Project  existingProject = projectRepos.findById(project.getId()).orElseThrow(
					()->new ProjectNotFoundException("Project not found in your account."));
			
			if(existingProject!=null&& (!existingProject.getProjectLeader().equals(name))) {
				throw new ProjectNotFoundException("Project not found in your account.");
			}
		}
		try {
			User user = userRepos.findByUsername(name)
					.orElseThrow(()-> new UsernameNotFoundException("No User found."));
			
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId()==null) {
				Backlog backlog= new Backlog();
				backlog.setProject(project);
				project.setBacklog(backlog);
				backlog.setProjectIdentifier(project.getProjectIdentifier());
			}
			if(project.getId()!=null) {
				project.setBacklog(backlogRepo.findByProjectIdentifier(project.getProjectIdentifier()).get());
			}
			return projectRepos.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project Identifier duplicated" + project.getProjectIdentifier().toUpperCase());
		}
	}

	public Project findByProjectIdentifier(String projectIdentifier,String username) {
		Project project = this.projectRepos.findByProjectIdentifier(projectIdentifier.toUpperCase()).orElseThrow(
				() -> new ProjectIdException("No Found with Project Identifier : " + projectIdentifier.toUpperCase()));
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectIdException("There is no Project with Identifier :" + projectIdentifier.toUpperCase());
		}
		return project;
	}

	public void deleteProjectByIdentifier(String projectId,String username) {
//		Project project = projectRepos.findByProjectIdentifier(projectId.toUpperCase()).orElseThrow(
//				() -> new ProjectIdException("There is no Project with Identifier : " + projectId.toUpperCase()));

		projectRepos.delete(findByProjectIdentifier(projectId, username));
	}
}
