package com.highway.resource;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.highway.domain.Project;
import com.highway.services.MapValidationErrorService;
import com.highway.services.ProjectService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin("*")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MapValidationErrorService validationService;

	@PostMapping("")
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project,
			Principal principal,
			BindingResult result) {
		
		if(result.hasErrors())return validationService.mapValidationService(result);
		
		Project newProject = this.projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(newProject,HttpStatus.CREATED);
	}
	
	
	@GetMapping("")
	public List<Project> getAllProjects(Principal principal){
		return projectService.getAllProjects(principal.getName());
	}
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId,Principal principal){
		Project project = projectService.findByProjectIdentifier(projectId,principal.getName());
		
		return new ResponseEntity<>(project,HttpStatus.OK);
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId,Principal principal){
		projectService.deleteProjectByIdentifier(projectId,principal.getName());
		return new ResponseEntity<String>(String.format("Project with %s was deleted", projectId),HttpStatus.OK);
	}
}
