package com.example.demo.model;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="Employee")
public class model {
	
	@Id
	private long id;
	private String firstname;
	private String lastname;
	private String branch;
	
	
	

	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getFirstname() {
		return firstname;
	}




	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}




	public String getLastname() {
		return lastname;
	}




	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




	public String getBranch() {
		return branch;
	}




	public void setBranch(String branch) {
		this.branch = branch;
	}




	public String toString() {
		return "\nid=\t "+id+" \nfirstname=\t "+firstname+" \nLastname=\t "+lastname+" \nBranch=\t "+branch ;
	}
}
