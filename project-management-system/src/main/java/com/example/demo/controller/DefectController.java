package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.DefectModel;
import com.example.demo.repo.CommentRepo;
import com.example.demo.repo.DefectHisRepo;
import com.example.demo.repo.DefectRepo;
import com.example.demo.repo.FieldValidation;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.SequenceGenerator;
import com.example.demo.services.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/Defect")
public class DefectController {
	
	@Autowired
	private DefectRepo drepo;
	
	@Autowired
	public DefectHisRepo hrepo;
	
	@Autowired
	CommentRepo crepo;

	@Autowired
	RoleValidation valid;
	
	@Autowired
	FieldValidation validField;
	
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	// Adding new Defect 
	@PostMapping("/addDefect")
	public HashMap<String, Object> addDefect(@RequestBody DefectModel defect, @RequestHeader (name="Authorization") String token) {
		String user = valid.getId(token);
		
		// Checking user is authorized or not
		if(valid.isValidUser(user, "addDefect")) {
			HashMap<String, Object> map = drepo.addDefects (defect, user);
			return map;
		}
		else {
			return valid.error();
		}
		
	}
	
	
	// Display all defects 
	@GetMapping("/displayDefects")
	public  HashMap<String, Object> getDefects( @RequestHeader (name="Authorization") String token) {
        if(valid.isValidUser(valid.getId(token), "displayDefects")) {  	
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	List<DefectModel> res = drepo.findAllValidDefects();
        	if(res.isEmpty() && res!=null) {
        		map.put("List of Defects : ", "No defects found!");
        		logger.info("No Records found in Defects");
        	}
        	else {
        		map.put("List of Defects : ", res);
        		logger.error("List of Defects Displayed");
        	}
        		
			System.out.println(map.size());
			return map;
        }
        else {
        	return valid.error();
        }
	}
	
	// Display defect by defect Id 
	@GetMapping("/getDefectByDefectId/{defectId}")
	public HashMap<String, Object> getDefectByDefectId(@PathVariable(value="defectId") String defectId, @RequestHeader (name="Authorization") String token){
			if(valid.isValidUser(valid.getId(token), "getDefectByDefectId")) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				DefectModel res = drepo.findByDefectId(defectId);
				if(res!=null)
					map.put("Defect : ", res);
				else
					map.put("Defect : ", "Defect does not exists");
				return map;
			}
			else {
				return valid.error();
			}
		
	}
	
	// Display list of defects of particular projectId 
	@GetMapping("/getDefectByProjectId/{projectId}")
	public HashMap<String, Object> getDefectByProjectId(@PathVariable(value="projectId") String projectId,  @RequestHeader (name="Authorization") String token) {
		if(valid.isValidUser(valid.getId(token), "getDefectByProjectId")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// Storing all list of projects in l
			List<DefectModel> l = drepo.findByProjectId(projectId);
			if(l.isEmpty())
				map.put("List of Defects : ","No project found" );
			else
				map.put("List of Defects : ", l);
			return map;
		}
		else {
			return valid.error();
		}
		
	}


	// Deleting defect record which means making status as inactive
	@DeleteMapping("/removeDefectByDefectId/{defectId}") 
	public HashMap<String, Object> removeDefectByDefectId(@PathVariable(value="defectId") String defectId, @RequestHeader (name="Authorization") String token) {
		String user = valid.getId(token);
		if(valid.isValidUser(user, "removeDefectByDefectId")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Defect : ", drepo.removeDefectByDefectId(defectId, user));
			return map;
		}
		else {
			return valid.error();
		}
	}


	
	// Updating particular defect - okay
	/* This also make some changes in defect history table when bug status or assigned To value is changed or
		or defect Status is set to closed. 
	If defect Status is closed, updations are done in comments table also
	*/
	@PatchMapping("/updateDefectByDefectId/{defectId}")
	public HashMap<String, Object> updateDefect(@PathVariable(value="defectId") String defectId, @RequestBody DefectModel defect, @RequestHeader (name="Authorization") String token) {
		String user = valid.getId(token);
		if(valid.isValidUser(user, "updateDefectByDefectId")) {
			
			HashMap<String, Object> map = drepo.updateDefects(defectId, defect, user);
			return map;
			
		}
		else {
			return valid.error();
		}
	}
	
	
}
