package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DefectRunningTrend")
public class DefectRunningTrend {
	
	@Id
	String date;
	int morningCount;
	int eveningCount;
	
	
	public DefectRunningTrend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DefectRunningTrend(String date, int morningCount, int eveningCount) {
		super();
		this.date = date;
		this.morningCount = morningCount;
		this.eveningCount = eveningCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getMorningCount() {
		return morningCount;
	}
	public void setMorningCount(int morningCount) {
		this.morningCount = morningCount;
	}
	public int getEveningCount() {
		return eveningCount;
	}
	public void setEveningCount(int eveningCount) {
		this.eveningCount = eveningCount;
	}
	
	
}
