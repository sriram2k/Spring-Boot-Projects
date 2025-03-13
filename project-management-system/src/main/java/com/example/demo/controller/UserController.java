package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.model.UserModel;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.SequenceGenerator;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.JwtTokenUtil;
import com.mongodb.client.result.DeleteResult;


@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

	@Autowired
	public UserRepo urepo;
	
	@Autowired
	RoleValidation valid;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	// Creating new user into the database
	@PostMapping("/addUser")
	public HashMap<String, Object> insertUserRecord(@RequestBody UserModel um, @RequestHeader(name="Authorization") String token) {
		if(valid.isValidUser(valid.getId(token), "addUser")) {
			HashMap<String, Object> hm =new HashMap<String, Object>();
			Pattern email = Pattern.compile("^(.+)@(.+)$");
			Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
			Pattern date = Pattern.compile(""
					+ "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
					+ "|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
					+ "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
			
			Matcher matcher = email.matcher(um.getEmailId());
			Matcher matcherpass = password.matcher(um.getPassword());
			Matcher matcherjoindate = date.matcher(um.getDateOfJoining());
			Matcher matcherregdate = date.matcher(um.getDateOfResigning());
			
			int err=0;
			
			
			if(um.getFirstName().trim()=="") {
				logger.error("Firstname can't be empty. Plaese Enter valid Firstname");
				hm.put("Firstname Error","Firstname can't be empty. Plaese Enter valid Firstname");
				err++;
			}
			if(um.getLastName().trim()=="") {
				logger.error("Lastname can't be empty. Plaese Enter valid Lastname");
				hm.put("Lastname Error","Lastname can't be empty. Plaese Enter valid Lastname");
				err++;
			}
			if(um.getUsername().trim()=="") {
				logger.error("Username can't be empty. Please Enter Valid Username");
				hm.put("Username Error","Username can't be empty. Please Enter Valid Username");
				err++;
			}
			if(um.getProjectId().trim()=="" || um.getProjectId().charAt(0)!='P') {
				logger.error("Project Id can't be empty. Please Enter Valid Project Id");
				hm.put("Project Id Error","Project Id can't be empty. Please Enter Valid Project Id");
				err++;
			}
			if((um.getMobileNumber().length()!=10)) {
				logger.error("Inalid Mobile Number or Email ID");
				hm.put("Mobile No Error","Inalid Mobile Number or Email ID");
				err++;
			}
			if(!matcherjoindate.matches()) {
				logger.error("Enter a correct Join Date");
				hm.put("Join Date Error","Enter a Valid Date format (01.01.2020,01/01/2020,01-01-2020)");
				err++;
			}
			if(um.getDateOfResigning().trim()=="") {
				logger.error("Date field can't be empty.");
				hm.put("Resigning Date Error","Date field can't be empty.Please Enter a valid Date");
				err++;
			}
			else {
				if((!um.getDateOfResigning().toLowerCase().equals("active") || !um.getDateOfResigning().toLowerCase().equals("present")) && 
						(!matcherregdate.matches())) {
					logger.error("Enter a correct Resigning Date");
					hm.put("Resigning Date Format Error","Enter a Valid Date format (01.01.2020,01/01/2020,01-01-2020)");
					err++;
				}
			}
			if(!matcherpass.matches()) {
				logger.error("Enter a Valid Password.");
				hm.put("Password Error","Password must contian atleast One Uppercase Letter, One Lowercase Letter, One Digit, One Special Character and Length (8 to 10 chars)");
				err++;
			}
			
			if( (!matcher.matches())){
				logger.error("Email id doesn't match. Enter a valid email id.");
				hm.put("Emailid Error","Email id doesn't match. Enter a valid email id.");
				err++;
			}
			
			if(um.getUserRoles().size()==0) {
				logger.error("A User must have atleast one role. Enter a valid role");
				hm.put("User Roles Error","A User must have atleast one role. Enter a valid role");
				err++;
				
			}
//			if(um.getUserStatus()!=null) {
//				logger.warn("Don't set user status .User Status will be set active");
//				hm.put("Status Warning","Don't set user status.User Status will be set active");
//				err++;
//				
//			}
			if(err>0) {
				return hm;
			}
			long userId=sequenceGenerator.generateUserSequence();
			um.setUserId("U"+userId);
			
			logger.info("Record Inserted Successfully");
			return urepo.save(um,valid.getId(token));
		}
		else {
			
			return valid.error();
		}
		
	}
	
	// This service fecthes all the user details from the database.
	@GetMapping("/getUsers")
	public HashMap<String, Object> getAllUserDetails(@RequestHeader (name="Authorization") String token) { 		
		if(valid.isValidUser(valid.getId(token), "getUsers")) {
			logger.info("Record Fetched Successfully");
			return urepo.findAll();
		}
		else
			
			return valid.error();
	}
	
	// This service fetches  a specific user detail from the database.
	@GetMapping("/getUser/{userId}")
	public HashMap<String, Object> getOneUserDetails(@PathVariable(value="userId") String uid, @RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "getUser")) {
			logger.info("Record Fetched Successfully");
			return urepo.findOne(uid);
		}
		else {
			return valid.error();
		}
	}
	
	// This service fetches  all the  user details by their role from the database.
	@GetMapping("/getUsersByRole/{role}")
	public HashMap<String, Object> geUserDetailsByRoleName(@PathVariable(value="role") String rolename,@RequestHeader (name="Authorization") String token) {
		if(valid.isValidUser(valid.getId(token), "getUsersByRole")) {
			logger.info("Record Fetched Successfully");
			return urepo.findAllRoles(rolename);
		}
		else {
			return valid.error();
		}		
	}
	
	// This service fetches  all the  user details grouped by their project id from the database.
	@GetMapping("/getUsersByProject/{pid}")
	public HashMap<String, Object> geUserDetailsByProjectId(@PathVariable(value="pid") String pid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "getUsersByProject")) {
			logger.info("Record Fetched Successfully");
			return urepo.findAllProjects(pid);
		}
		else {
			return valid.error();
		}
		
	}
	
	// This service updates the user details in the database.
	@PatchMapping("/updateUser/{userId}")
	public HashMap<String, Object> updateOneUserDetails(@RequestBody UserModel um,@PathVariable(value="userId") String uid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "updateUser")) {
			
		HashMap<String, Object> hm =new HashMap<String, Object>();
		Pattern email = Pattern.compile("^(.+)@(.+)$");
		Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		Pattern date = Pattern.compile(""
				+ "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
				+ "|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
				+ "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		
		Matcher matcher = email.matcher(um.getEmailId());
		Matcher matcherpass = password.matcher(um.getPassword());
		Matcher matcherjoindate = date.matcher(um.getDateOfJoining());
		Matcher matcherregdate = date.matcher(um.getDateOfResigning());
		
		int err=0;
		
		if(um.getFirstName()!=null && um.getFirstName().trim()=="") {
			logger.error("Firstname can't be empty. Plaese Enter valid Firstname");
			hm.put("Firstname Error","Firstname can't be empty. Plaese Enter valid Firstname");
			err++;
		}
		if(um.getLastName()!=null && um.getLastName().trim()=="") {
			logger.error("Lastname can't be empty. Plaese Enter valid Lastname");
			hm.put("Lastname Error","Lastname can't be empty. Plaese Enter valid Lastname");
			err++;
		}
		if(um.getUsername().trim()=="") {
			logger.error("Username can't be empty. Please Enter Valid Username");
			hm.put("Username Error","Username can't be empty. Please Enter Valid Username");
			err++;
		}
		if(um.getProjectId().trim()=="" || um.getProjectId().charAt(0)!='P') {
			logger.error("Project Id can't be empty. Please Enter Valid Project Id");
			hm.put("Project Id Error","Project Id can't be empty. Please Enter Valid Project Id");
			err++;
		}
		if((um.getMobileNumber().length()!=10)) {
			logger.error("Inalid Mobile Number or Email ID");
			hm.put("Mobile No Error","Inalid Mobile Number or Email ID");
			err++;
		}
		if(!matcherjoindate.matches()) {
			logger.error("Enter a correct Join Date");
			hm.put("Join Date Error","Enter a Valid Date format (01.01.2020,01/01/2020,01-01-2020)");
			err++;
		}
		if(um.getDateOfResigning().trim()=="") {
			logger.error("Date field can't be empty.");
			hm.put("Resigning Date Error","Date field can't be empty.Please Enter a valid Date");
			err++;
		}
		else {
			if((!um.getDateOfResigning().toLowerCase().equals("active") || !um.getDateOfResigning().toLowerCase().equals("present")) && 
					(!matcherregdate.matches())) {
				logger.error("Enter a correct Resigning Date");
				hm.put("Resigning Date Format Error","Enter a Valid Date format (01.01.2020,01/01/2020,01-01-2020)");
				err++;
			}
		}
		if(!matcherpass.matches()) {
			logger.error("Enter a Valid Password.");
			hm.put("Password Error","Password must contian atleast One Uppercase Letter, One Lowercase Letter, One Digit, One Special Character and Length (8 to 10 chars)");
			err++;
		}
		
		if( (!matcher.matches())){
			logger.error("Email id doesn't match. Enter a valid email id.");
			hm.put("Emailid Error","Email id doesn't match. Enter a valid email id.");
			err++;
		}
		
		if(um.getUserRoles().size()==0) {
			logger.error("A User must have atleast one role. Enter a valid role");
			hm.put("User Roles Error","A User must have atleast one role. Enter a valid role");
			err++;
			
		}
//		if(um.getUserStatus()!=null) {
//			logger.warn("Don't set user status .User Status will be set active");
//			hm.put("Status Warning","Don't set user status.User Status will be set active");
//			err++;
//			
//		}
		if(err>0) {
			return hm;
		}
		logger.info("Record Updated Successfully");
			return urepo.updateOneUser(um,uid,valid.getId(token));
		}
		else {
			return valid.error();
		}
	}
	
	
	// This service deletes a particular record from the database.
	@DeleteMapping("/deleteUser/{userId}")
	public HashMap<String, Object> deleteOneUserDetails(@PathVariable(value="userId") String uid,@RequestHeader (name="Authorization") String token) { 
		if(valid.isValidUser(valid.getId(token), "deleteUser")) {
			logger.info("Record Deleted Successfully");
			return urepo.deleteOne(uid,valid.getId(token));
		}
		else {
			return valid.error();
		}
	}
	
}
