package com.highway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highway.domain.Backlog;
import com.highway.domain.PStatus;
import com.highway.domain.ProjectTask;
import com.highway.exceptions.ProjectNotFoundException;
import com.highway.repos.BacklogRepo;
import com.highway.repos.ProjectRepos;
import com.highway.repos.ProjectTaskRepo;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepo backlogRepo;
	@Autowired
	private ProjectTaskRepo projectTaskRepo;
	
	@Autowired
	private ProjectRepos  projectRepos;
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

	public List<ProjectTask> findBacklogById(String id) {
		projectRepos.findByProjectIdentifier(id).orElseThrow(
					()->  new ProjectNotFoundException(String.format("Project With ID %s does not exist.", id))
				);
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findByProjectSequence(String backlogId,String ptId) {
		Backlog backlog = backlogRepo.findByProjectIdentifier(backlogId)
				.orElseThrow(()->new ProjectNotFoundException(String.format("Project with id %s does not exist.", backlogId)));
		
		ProjectTask projectTask=projectTaskRepo.findByProjectSequence(ptId).orElseThrow(()->
		new ProjectNotFoundException("Project Task  '" + ptId + "' not found."));
		
		if(!projectTask.getProjectIdentifier().equals(backlog.getProjectIdentifier())) {
			throw new ProjectNotFoundException("Project task '" +ptId +"' does not exist in project : " + backlogId);
		}
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask task,String backlogId,String taskId) {
		ProjectTask projectTask = this.findByProjectSequence(backlogId, taskId);
		projectTask =task;
		return projectTask;
	}
	public void deleteProjectTask(String backlogId,String taskId) {
		
		ProjectTask projectTask = this.findByProjectSequence(backlogId, taskId);
		projectTask.getBacklog().removeProjectTask(projectTask);
		projectTaskRepo.delete(projectTask);
	}
}
