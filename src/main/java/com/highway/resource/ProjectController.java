package com.highway.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.highway.domain.Project;
import com.highway.services.MapValidationErrorService;
import com.highway.services.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MapValidationErrorService validationService;

	@PostMapping("")
	public ResponseEntity<?> name(@Valid @RequestBody Project project,
			BindingResult result) {
		
		if(result.hasErrors())return validationService.mapValidationService(result);
		
		Project newProject = this.projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(newProject,HttpStatus.CREATED);
	}
}
