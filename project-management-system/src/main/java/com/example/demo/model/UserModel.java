package com.example.demo.model;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection="UserDetails")
public class UserModel {

	@Id
	String userId;
	String username;
	String password;
	String firstName;
	String lastName;
	String projectId;
	String emailId;
	String mobileNumber;
	String createdBy;
	String lastUpdatedBy;
	Date lastUpdatedDate;
	String dateOfJoining;
	String dateOfResigning;
	Set<String> userRoles;
	String userStatus;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getDateOfResigning() {
		return dateOfResigning;
	}
	
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserModel(String userId, String username, String password, String firstName, String lastName,
			String projectId, String emailId, String mobileNumber, String createdBy,
			String lastUpdatedBy, Date lastUpdatedDate, String dateOfJoining, String dateOfResigning,
			Set<String> userRoles, String userStatus) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.projectId = projectId;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.dateOfJoining = dateOfJoining;
		this.dateOfResigning = dateOfResigning;
		this.userRoles = userRoles;
		this.userStatus = userStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public void setDateOfResigning(String dateOfResigning) {
		this.dateOfResigning = dateOfResigning;
	}
	public Set<String> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<String> userRoles) {
		this.userRoles = userRoles;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
