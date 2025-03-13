package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection="Services")

public class ServiceModel {
	
	@Id
	private String serviceName;
	private Set<String> rolesPermission;
	String createdBy;
	String lastUpdatedBy;
	Date lastUpdatedDate;
	public ServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceModel(String serviceName, Set<String> rolesPermission, String createdBy, String lastUpdatedBy,
			Date lastUpdatedDate) {
		super();
		this.serviceName = serviceName;
		this.rolesPermission = rolesPermission;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Set<String> getRolesPermission() {
		return rolesPermission;
	}
	public void setRolesPermission(Set<String> rolesPermission) {
		this.rolesPermission = rolesPermission;
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
	

	
	
	
}
