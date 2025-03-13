package com.example.demo.model;

import java.util.List;

public class RTM {
	String requirementId;
	List<String> testcases;
	String status;
	public RTM() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RTM(String requirementId, List<String> testcases, String status) {
		super();
		this.requirementId = requirementId;
		this.testcases = testcases;
		this.status = status;
	}
	public String getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	public List<String> getTestcases() {
		return testcases;
	}
	public void setTestcases(List<String> testcases) {
		this.testcases = testcases;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RTM [requirementId=" + requirementId + ", testcases=" + testcases + ", status=" + status + "]";
	}
	
	
	
	
}
