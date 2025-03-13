package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.model.ServiceModel;
import com.example.demo.model.UserModel;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.SequenceGenerator;
import com.mongodb.client.result.DeleteResult;


@RestController
@RequestMapping("/api/v1/role/")
public class RoleController {

	@Autowired
	RoleRepo rrepo;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	RoleValidation valid;
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	// Adding new Role into the database
	@PostMapping("/addRole")
	public HashMap<String, Object> insertNewRole(@RequestBody RoleModel um,@RequestHeader (name="Authorization") String token) {
		String userid =valid.getId(token);
		if(valid.isValidUser(userid, "addRole")) {
			HashMap<String, Object> hm =new HashMap<String, Object>();
			
			if(um.getRoleName().trim()=="") {
				logger.warn("Role Name cannot be empty.");
				hm.put("Error","Role Name cannot be empty. Enter a Role Name");
				return hm;
			}
			long roleId=sequenceGenerator.generateRoleSequence();
			um.setRoleId("R"+roleId);
			logger.info(" Record Inserted Successfully ");
			return rrepo.save(um,valid.getId(token));
		}
		else {
			logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
		
	}
	// This method fetches all the Role Details from the Database
	@GetMapping("/getAllRoles")
	public HashMap<String, Object> getAllRoles(@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "getAllRoles")) {
			logger.info(" Record Fetched Successfully ");
			return rrepo.findAll();
		}
		else {
			logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	
	// This method fetches the particular Role Detail with respect to roleID from the Database
	@GetMapping("/getRole/{roleId}")
	public HashMap<String, Object> getOneRole(@PathVariable(value="roleId") String uid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "getRole")) {
			logger.info(" Record Fetched Successfully ");
			return rrepo.findOne(uid);
		}
		else {
			logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
		
	}
	
	// This methods updates the particular Role record with respect to roleID
	@PatchMapping("/updateRole/{roleId}")
	public HashMap<String, Object> updateOneRole(@RequestBody RoleModel um,@PathVariable(value="roleId") String uid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "updateRole")) {
			HashMap<String, Object> hm =new HashMap<String, Object>();
			if(um.getRoleName().trim()=="") {
				logger.warn("Role Name cannot be empty.");
				hm.put("Error","Role Name cannot be empty. Enter a Role Name to be updated");
				return hm;
			}
			logger.info("Record Updated Successfully ");
			return rrepo.updateOneRole(um,uid,valid.getId(token));
		}
		else {
			logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
		
	}
	
	// This methods changes the userStatus to inactive in the database with respect to their id
	@DeleteMapping("/deleteRole/{roleId}")
	public HashMap<String, Object> deleteOneRole(@PathVariable(value="roleId") String rid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "deleteRole")) {
			logger.info("Record Deleted Successfully ");
			return rrepo.deleteOne(rid,valid.getId(token));
		}
		else {
			logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
		
	}
}
