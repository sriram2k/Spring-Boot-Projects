package com.example.demo.model;

public class Requirement {
	private String requirementNo;
	private String requirementDescription;
	private String status;
	public Requirement() {
		super();
	}
	public Requirement(String requirementNo, String requirementDescription, String status) {
		super();
		this.requirementNo = requirementNo;
		this.requirementDescription = requirementDescription;
		this.status = status;
	}
	public String getRequirementNo() {
		return requirementNo;
	}
	public void setRequirementNo(String requirementNo) {
		this.requirementNo = requirementNo;
	}
	public String getRequirementDescription() {
		return requirementDescription;
	}
	public void setRequirementDescription(String requirementDescription) {
		this.requirementDescription = requirementDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "requirement [requirementNo=" + requirementNo + ", requirementDescription=" + requirementDescription
				+ ", status=" + status + "]";
	}
	
}

