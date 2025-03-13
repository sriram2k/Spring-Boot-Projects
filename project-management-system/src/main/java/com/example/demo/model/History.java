package com.example.demo.model;

import java.util.Date;

public class History {
	private int eventId;
	private String status;
	private Date startDate;
	private Date endDate;
	private String workDoneBy;
	
	public History() {
		super();
		// TODO Auto-generated constructor stub
	}

	public History(int eventId, String status, Date startDate, Date endDate, String workDoneBy) {
		super();
		this.eventId = eventId;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.workDoneBy = workDoneBy;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getWorkDoneBy() {
		return workDoneBy;
	}

	public void setWorkDoneBy(String workDoneBy) {
		this.workDoneBy = workDoneBy;
	}
	

}
