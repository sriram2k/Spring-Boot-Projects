package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DefectHistory")
public class DefectHisModel {
	
	@Id
	private String defectId;
	private String defectStatus;
	private List<History> defectHistory;
	
	public DefectHisModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DefectHisModel(String defectId, String defectStatus, List<History> defectHistory) {
		super();
		this.defectId = defectId;
		this.defectStatus = defectStatus;
		this.defectHistory = defectHistory;
	}
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
	public List<History> getDefectHistory() {
		return defectHistory;
	}
	public void setDefectHistory(List<History> defectHistory) {
		this.defectHistory = defectHistory;
	}	
}
