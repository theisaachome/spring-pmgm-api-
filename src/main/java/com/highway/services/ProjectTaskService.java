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
	
	public ProjectTask addProjectTask(String projectIdentifier,
			ProjectTask projectTask) {
		
		try {
			// PTs to be add to specific project, project !=null. bl exist!.
			Backlog backlog = backlogRepo.findByProjectIdentifier(projectIdentifier).orElseThrow(()->
			new ProjectNotFoundException("Backlog not found"));
			//  set bl to pj
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
		} catch (Exception e) {
			// TODO: handle exception
			throw new ProjectNotFoundException("Project Not found from trycatch");
		}
	}

	public List<ProjectTask> findBacklogById(String id) {
		projectRepos.findByProjectIdentifier(id).orElseThrow(
					()->  new ProjectNotFoundException(String.format("Project With ID %s does not exist.", id))
				);
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findByProjectSequence(String backlogId,String ptId) {
		return projectTaskRepo.findByProjectSequence(ptId).orElseThrow(()->
		new ProjectNotFoundException("Project Task  not found."));
	}
}
