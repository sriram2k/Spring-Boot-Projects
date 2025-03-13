package com.example.demo.controller;

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

import com.example.demo.model.DefectHisModel;
import com.example.demo.model.DefectModel;
import com.example.demo.model.History;
import com.example.demo.repo.DefectHisRepo;
import com.example.demo.repo.RoleValidation;



@RestController
@RequestMapping("/api/v1/DefectHistory")
public class DefectHisController {
	
	@Autowired
	public DefectHisRepo hrepo;
	
	@Autowired
	RoleValidation valid;
		
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	// Displaying all defect history - tested
		@GetMapping("/displayDefectHistory")
		public  HashMap<String, Object> getDefectHistory(@RequestHeader (name="Authorization") String token) {
			if(valid.isValidUser(valid.getId(token), "displayDefectHistory")) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				List<DefectHisModel> l =  hrepo.findAll();
				if(l!=null && !l.isEmpty()) {
					map.put("History of Defects : ",l);
					logger.info("List of Defect History is displayed");
				}
				else {
					map.put("Result : ", "No Records found in Defect History");
					logger.error("No Records found in Defect History");
				}
				return map;
			}
			else {
				return valid.error();
			}
		}

		
		// Display defect history by defect Id - tested
		@GetMapping("/getDefectHistoryByDefectId/{defectId}")
		public HashMap<String, Object> getDefectHisByDefectId(@RequestHeader (name="Authorization") String token, @PathVariable(value="defectId") String defectId){
			if(valid.isValidUser(valid.getId(token), "getDefectHistoryByDefectId")) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				DefectHisModel res = hrepo.findDefectHistoryByDefectId(defectId);
				if(res!=null) {
					map.put("Defect History : ", res);
					logger.info("Specified Defect History Displayed");
				}
				else {
					map.put("Defect History : ", "Defect History not found");
					logger.error("No such defect history record found");
				}
				return map;
			}
			else {
				return valid.error();
			}
		}
	
}
