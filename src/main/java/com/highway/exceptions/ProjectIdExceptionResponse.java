package com.highway.exceptions;


public class ProjectIdExceptionResponse {
	
	private String projectNotFound;
	
	public ProjectIdExceptionResponse(String projectIdentifier) {
		this.projectNotFound=projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectNotFound;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectNotFound = projectIdentifier;
	}
	
	

}
