package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Projects")
public class Project {
	@Id
	private String projectId;
	private String projectName;
	private String projectDescription;
	private Date startDate;
	private Date endDate;
	private String targetedRelease;
	private String status;
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	private List<Requirement> requirements;

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Project(String projectId, String projectName, String projectDescription, Date startDate, Date endDate,
			String targetedRelease, String status, String createdBy, String updatedBy, Date lastUpdatedDate,
			List<Requirement> requirements) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.targetedRelease = targetedRelease;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.requirements = requirements;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTargetedRelease() {
		return targetedRelease;
	}

	public void setTargetedRelease(String targetedRelease) {
		this.targetedRelease = targetedRelease;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectName=" + projectName + ", projectDescription="
				+ projectDescription + ", startDate=" + startDate + ", endDate=" + endDate + ", targetedRelease="
				+ targetedRelease + ", status=" + status + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", requirements=" + requirements + "]";
	}

	
}
