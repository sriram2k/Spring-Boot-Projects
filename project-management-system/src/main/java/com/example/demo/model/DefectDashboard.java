package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DefectDashboard")
public class DefectDashboard {
	
	@Id
	private Date date;
	private int New;
	private int open;
	private int fixed;
	private int retest;
	private int closed;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNew() {
		return New;
	}
	public void setNew(int new1) {
		New = new1;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getFixed() {
		return fixed;
	}
	public void setFixed(int fixed) {
		this.fixed = fixed;
	}
	public int getRetest() {
		return retest;
	}
	public void setRetest(int retest) {
		this.retest = retest;
	}
	
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	public DefectDashboard(Date date, int new1, int open, int fixed, int retest, int failed) {
		super();
		this.date = date;
		New = new1;
		this.open = open;
		this.fixed = fixed;
		this.retest = retest;
		this.closed = closed;
	}
	public DefectDashboard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
