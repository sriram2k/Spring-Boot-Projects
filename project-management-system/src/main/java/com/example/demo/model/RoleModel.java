package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection="Roles")
public class RoleModel {

	@Id
	String roleId;
	String roleName;
	String roleStatus;
	String createdBy;
	String lastUpdatedBy;
	Date lastUpdatedDate;
	
	public RoleModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public RoleModel(String roleId, String roleName, String roleStatus, String createdBy, String lastUpdatedBy,
			Date lastUpdatedDate) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
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



	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	
	
	

}
