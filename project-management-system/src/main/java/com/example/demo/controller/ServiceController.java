package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.demo.model.ServiceModel;
import com.example.demo.model.UserModel;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.ServiceRepo;
import com.mongodb.client.result.DeleteResult;


@RestController
@RequestMapping("/api/v1/service/")
public class ServiceController {

	@Autowired
	ServiceRepo srepo;
	
	@Autowired
	RoleValidation valid;
	
	Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	// Creating new service and assigning permissions into the database
	@PostMapping("/addService")
	public HashMap<String, Object> insertService(@RequestBody ServiceModel um,@RequestHeader (name="Authorization") String token) {
		if(valid.isValidUser(valid.getId(token), "addService")) {
			HashMap<String, Object> hm =new HashMap<String, Object>();
			if(um.getServiceName().trim()=="") {
				logger.error("Service Name cannot be empty.");
				hm.put("Error","Service Name cannot be empty. Enter a Service Name");
				return hm;
			}
			if(um.getRolesPermission().size()==0) {
				logger.error("Assign permissions for the Service.");
				hm.put("Error","Permissions can't be empty. Assign Permissions for the service");
				return hm;
			}
			logger.info(" Record Inserted Successfully ");
			return srepo.save(um,valid.getId(token));
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	// This service fetches all the services from the database.
	@GetMapping("/getServices")
	public HashMap<String, Object> getAllServices(@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "getServices")) {
			logger.info(" Record Fetched Successfully ");
			return srepo.findAll();
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	// This service fetches  a specific service from the database.
	@GetMapping("/getServiceByRole/{role}")
	public HashMap<String, Object> getServiceByRole(@PathVariable(value="role") String role,@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "getServiceByRole")) {
			logger.info(" Record Fetched Successfully ");
			return srepo.findByRole(role);
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	// This service fetches a service  by their name from the database.
	@GetMapping("/getServiceByName/{servicename}")
	public HashMap<String, Object> getServiceByName(@PathVariable(value="servicename") String servicename,@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "getServiceByName")) {
			logger.info(" Record Fetched Successfully ");
			return srepo.findByService(servicename);
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	// This service updates the permission for a service into the permissions array.
	@PatchMapping("/updatePermission/{servicename}")
	public HashMap<String, Object> updatePermission(@RequestBody ServiceModel um,@PathVariable(value="servicename") String servicename,@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "updatePermission")) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			if(um.getRolesPermission().size()==0) {
				logger.error("Assign permissions for the Service.");
				hm.put("Error","Permissions can't be empty. Assign Permissions for the service to be added.");
				return hm;
			}
			logger.info(" Record Updated Successfully ");
			return srepo.updatePermissions(um,servicename,valid.getId(token));
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	// This service removes the permission from the permission array.
	@PatchMapping("/removePermission/{servicename}")
	public HashMap<String, Object> removePermission(@RequestBody ServiceModel um,@PathVariable(value="servicename") String servicename,@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "removePermission")) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			if(um.getRolesPermission().size()==0) {
				logger.error("Assign permissions for the Service.");
				hm.put("Error","Permissions can't be empty. Assign Permissions for the service to be removed.");
				return hm;
			}
			logger.info(" Record Updated Successfully ");
			return srepo.removePermissions(um,servicename,valid.getId(token));
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
	
	
	// This service deletes the service by its name.
	@DeleteMapping("/deleteService/{sname}")
	public HashMap<String, Object> deleteService(@PathVariable(value="sname") String sname,@RequestHeader (name="Authorization") String token) { 
		
		if(valid.isValidUser(valid.getId(token), "deleteService")) {
			logger.info(" Record Deleted Successfully ");
			return srepo.deleteService(sname);
		}
		else {logger.error("You are not Authorized to use this Service.");
			return valid.error();
		}
	}
}
