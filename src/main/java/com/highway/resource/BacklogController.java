package com.highway.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.highway.domain.ProjectTask;
import com.highway.services.MapValidationErrorService;
import com.highway.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin("*")
public class BacklogController {
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> createTask(@Valid 
			@RequestBody ProjectTask projectTask,
			BindingResult result,
			@PathVariable String backlog_id) {
		if(result.hasErrors()) mapValidationErrorService.mapValidationService(result);
		
		ProjectTask newProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<>(newProjectTask,HttpStatus.CREATED);
	}

	@GetMapping("/{backlog_id}")
	public List<ProjectTask> getProjectBlacklog(
			@PathVariable String backlog_id) {
		return projectTaskService.findBacklogById(backlog_id);
	}

	@GetMapping("/{backlogId}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId,
			@PathVariable String pt_id){
		
		ProjectTask projectTask= projectTaskService.findByProjectSequence(backlogId,pt_id);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
}
