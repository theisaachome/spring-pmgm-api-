package com.highway.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer PTSequence=0;
	private String projectIdentifier;

	
//	One-to-One with Project. Each Project has only one Project
	@OneToOne
	@JoinColumn(name = "project_id",nullable = false)
	@JsonIgnore
	private Project project;
	
	
//	 One-to-many with Backlog and its ProjectTask
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,mappedBy = "backlog",
			orphanRemoval = true)
	private List<ProjectTask> projectTasks = new ArrayList<>(); 
	
	public Backlog(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getPTSequence() {
		return PTSequence;
	}
	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTasks.add(projectTask);
	}
	public void removeProjectTask(ProjectTask projectTask) {
		this.projectTasks.remove(projectTask);
	}
	
	
	

}
