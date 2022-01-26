package com.highway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highway.domain.Backlog;
import com.highway.domain.PStatus;
import com.highway.domain.ProjectTask;
import com.highway.domain.User;
import com.highway.exceptions.ProjectNotFoundException;
import com.highway.repos.BacklogRepo;
import com.highway.repos.ProjectTaskRepo;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepo backlogRepo;
	@Autowired
	private ProjectTaskRepo projectTaskRepo;
	

	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTask(String projectIdentifier,
			ProjectTask projectTask,String username) {
		
			// PTs to be add to specific project, project !=null. bl exist!.
			Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();			//  set bl to pj
			projectTask.setBacklog(backlog);
			// set PTSequence
			Integer blacklogSequence = backlog.getPTSequence();
			// update Backlog SEQUENCE
			blacklogSequence++;
			backlog.setPTSequence(blacklogSequence);
			// add sequence to Project Task
			projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+blacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			projectTask.setStatus(PStatus.TO_DO);
			
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}
				
			return projectTaskRepo.save(projectTask);
	}

	public List<ProjectTask> findBacklogById(String id,String username) {
		projectService.findByProjectIdentifier(id,username);
		
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findByProjectSequence(String backlogId,String ptId,String username) {
		projectService.findByProjectIdentifier(backlogId, username).getBacklog();
		
		ProjectTask projectTask=projectTaskRepo.findByProjectSequence(ptId).orElseThrow(()->
		new ProjectNotFoundException("Project Task  '" + ptId + "' not found."));
		
		if(!projectTask.getProjectIdentifier().equals(backlogId)) {
			throw new ProjectNotFoundException("Project task '" +ptId +"' does not exist in project : " + backlogId);
		}
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask task,String backlogId,String taskId,String username) {
		ProjectTask projectTask = this.findByProjectSequence(backlogId, taskId,username);
		projectTask =task;
		
		return projectTaskRepo.save(projectTask);
	}
	public void deleteProjectTask(String backlogId,String taskId,String username) {
		
		ProjectTask projectTask = this.findByProjectSequence(backlogId, taskId,username);
		projectTask.getBacklog().removeProjectTask(projectTask);
		projectTaskRepo.delete(projectTask);
	}
}
