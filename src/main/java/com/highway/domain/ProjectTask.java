package com.highway.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

@Entity
public class ProjectTask {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false)
	private String projectSequence;
	@NotBlank(message = "Please include summary.")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	private LocalDate dueDate;
	
	private LocalDate createdAt;
	private LocalDate updateAt;
	
	
//	Many to One to backlog
	@Column(updatable = false)
	private String projectIdentifier;
//	Many to One to Project
	
	public ProjectTask() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectSequence() {
		return projectSequence;
	}
	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}
	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(LocalDate updateAt) {
		this.updateAt = updateAt;
	}
	
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	
	@Override
	public String toString() {
		return "Task [id=" + id + ","
				+ " projectSequence=" + projectSequence 
				+ ", summary=" + summary
				+ ", acceptanceCriteria=" 
				+ acceptanceCriteria 
				+ ", status=" + status 
				+ ", priority=" + priority
				+ ", dueDate=" + dueDate 
				+ ", createdAt=" + createdAt 
				+ ", updateAt=" + updateAt
				+ ", projectIdentifier=" 
				+ projectIdentifier + "]";
	}

	@PrePersist
	protected void onCreated() {
		this.createdAt = LocalDate.now();
	}
	@PreUpdate
	protected void onUpdated() {
		this.updateAt = LocalDate.now();
	}
	
}
