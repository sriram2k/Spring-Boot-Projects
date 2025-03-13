package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Testcases")
public class Testcase {
	@Id
	private String testcaseId;
	private String requirementId;
	private String projectId;
	private String testcaseName;
	private String description;
	private String expectedResults;
	private String actualResults;
	private String status;
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	
	

	private Testcase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Testcase(String testcaseId, String requirementId, String projectId, String testcaseName, String description,
			String expectedResults, String actualResults, String status, String createdBy, String updatedBy,
			Date lastUpdatedDate) {
		super();
		this.testcaseId = testcaseId;
		this.requirementId = requirementId;
		this.projectId = projectId;
		this.testcaseName = testcaseName;
		this.description = description;
		this.expectedResults = expectedResults;
		this.actualResults = actualResults;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
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

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpectedResults() {
		return expectedResults;
	}

	public void setExpectedResults(String expectedResults) {
		this.expectedResults = expectedResults;
	}

	public String getActualResults() {
		return actualResults;
	}

	public void setActualResults(String actualResults) {
		this.actualResults = actualResults;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Testcase [projectId=" + projectId + ", requirementId=" + requirementId + ", testcaseId=" + testcaseId
				+ ", testcaseName=" + testcaseName + ", description=" + description + ", expectedResults="
				+ expectedResults + ", actualResults=" + actualResults + ", status=" + status + "]";
	}
	
	
	
	
}
