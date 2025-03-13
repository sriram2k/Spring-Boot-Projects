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

import com.example.demo.model.CommentDesModel;
import com.example.demo.model.CommentModel;
import com.example.demo.repo.CommentRepo;
import com.example.demo.repo.RoleValidation;


@RestController
@RequestMapping("/api/v1/Comment")
public class CommentController {
	
	@Autowired
	CommentRepo crepo;
	
	@Autowired
	RoleValidation valid;
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	// Displaying all comments - tested
	@GetMapping("/displayComments")
	public  HashMap<String, Object> getDisplayComments(@RequestHeader (name="Authorization") String token) {
		if(valid.isValidUser(valid.getId(token), "displayComments")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<CommentModel> l = crepo.findAll();
			if(l.isEmpty() || l==null) {
				map.put("Result : ", "No records found in Comments");
				logger.info("No records found in Comments");
			}
			else {
				map.put("List of Comments : ", l);
				logger.info("List of Comments is displayed");
			}
			
			return map;
		}
		else {
			return valid.error();
		}	 
		
	}
	
	// Chat application - tested
	@PatchMapping("/addChat/{defectId}")
	public HashMap<String, Object> addChat(@RequestBody CommentDesModel chat, @PathVariable(value = "defectId") String defectId, @RequestHeader (name="Authorization") String token) {
		String user = valid.getId(token);
		if(valid.isValidUser(user, "addChat")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			CommentModel res = crepo.addChat(defectId,chat, user);
			if(res!=null) {
				map.put("Updated Comment : ",  res);
				logger.info("One Comment is added");
			}
			else {
				map.put("Result : ", "Comment does not exists");
				logger.info("No comments found for specific defect");
			}
				
			return map;
		}
		else {
			return valid.error();
		}
	}
	
	// Display comment of particular defect Id - tested 
	@GetMapping("/getCommentByDefectId/{defectId}")
	public HashMap<String, Object> getCommentByDefectId(@PathVariable(value="defectId") String defectId, @RequestHeader (name="Authorization") String token){
		if(valid.isValidUser(valid.getId(token), "getCommentByDefectId")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			CommentModel res = crepo.findCommentByDefectId(defectId);
			if(res!=null) {
				map.put("Comment : ",  res);
				logger.info("One comment is viewed");
			}
				
			else {
				map.put("Result : ", "Comment does not exists");
				logger.info("No comments found for specific defect Id");
			}
				
			return map;
		}
		else {
			return valid.error();
		}
	}


}
