package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Defect")
public class DefectModel {
	
	@Id
	private String defectId;
	private String defectStatus;
	private Date createdDate;
	private String projectId;
	private String testCaseId;
	private String expectedResults;
	private String actualResults;
	private String assignedTo;
	
	private String createdBy;
	private String updatedBy;
	private Date lastUpdatedDate;
	
	private String bugStatus;
	private int severity;
	private String defectType;
	private Set<String> attachmentLinks;
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
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
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
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
	public String getBugStatus() {
		return bugStatus;
	}
	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public Set<String> getAttachmentLinks() {
		return attachmentLinks;
	}
	public void setAttachmentLinks(Set<String> attachmentLinks) {
		this.attachmentLinks = attachmentLinks;
	}
	public DefectModel(String defectId, String defectStatus, Date createdDate, String projectId, String testCaseId,
			String expectedResults, String actualResults, String assignedTo, String createdBy, String updatedBy,
			Date lastUpdatedDate, String bugStatus, int severity, String defectType, Set<String> attachmentLinks) {
		super();
		this.defectId = defectId;
		this.defectStatus = defectStatus;
		this.createdDate = createdDate;
		this.projectId = projectId;
		this.testCaseId = testCaseId;
		this.expectedResults = expectedResults;
		this.actualResults = actualResults;
		this.assignedTo = assignedTo;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.bugStatus = bugStatus;
		this.severity = severity;
		this.defectType = defectType;
		this.attachmentLinks = attachmentLinks;
	}
	public DefectModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
