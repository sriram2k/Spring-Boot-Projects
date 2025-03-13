package com.example.demo.repo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.model.Project;
import com.example.demo.model.Testcase;
import com.example.demo.model.UserModel;

@Component
public class FieldValidation {
	
	@Autowired 
	ProjectRepo prepo;
	
	@Autowired
	TestcaseRepo trepo;
	
	@Autowired
	UserRepo urepo;
	
	
	
	public boolean isValidProjectId(String projectId) {
		if(projectId == null)
			return false;
		if(projectId.isEmpty() || projectId.trim().isEmpty())
			return false;
		else {
			Project p = prepo.getProjectByPid(projectId);
			if(p.equals(null) || p.getStatus().toLowerCase().equals("inactive"))
				return false;
		}
		
		return true;
	}
	
	public boolean isValidTestcaseId(String testcaseId) {
		if(testcaseId == null)
			return false;
		if(testcaseId.isEmpty() || testcaseId.trim().isEmpty())
			return false;
		else {
			
			Testcase t = trepo.getTestcaseByTestcaseId(testcaseId);
			if(t == null || t.equals(null) || t.getStatus().toLowerCase().equals("inactive"))
				return false;
		}
		return true;
	}
	
	public boolean isValidEmployee(String userId) {
		if(userId == null)
			return false;
		if(userId.isEmpty() || userId.trim().isEmpty()){
			return false;
		}
		else {
			
			UserModel um = (UserModel) (urepo.findOne(userId)).get("UserDetail");
			if(um == null || um.getUserStatus().toLowerCase().equals("inactive"))
				return false;
		}
		return true;
	}
	
	public boolean isValidString(String s) {
		if(s == null || s.trim().isEmpty() || s.isEmpty())
			return false;
		return true;
	}
	public boolean isValidBugStatus(String bug) {
		if(bug == null)
			return false;
		if(bug.trim().toLowerCase().equals("new") || bug.trim().toLowerCase().equals("open") || bug.trim().toLowerCase().equals("fixed") || 
				bug.trim().toLowerCase().equals("retest") || bug.trim().toLowerCase().equals("verified")) {
			return true;
		}
		
		return false;
	}
	
	public boolean isValidSeverity(int value) {
		
		if(value == 1 || value == 2 || value == 3)
			return true;
		return false;
	}
	
	public boolean isValidDefectType(String defectType) {
		if(defectType == null || defectType.trim().isEmpty() || defectType.isEmpty())
			return false;
		if(defectType.trim().toLowerCase().contains("requirement") || defectType.trim().toLowerCase().contains("not a requirement") ||
				defectType.trim().toLowerCase().contains("wad") || defectType.trim().toLowerCase().contains("invalid defect"))
			return true;
		return false;
	}
	
	public boolean isValidAttachment(Set<String> attach) {
		if(attach == null || attach.isEmpty())
			return false;
		List<String> list = new ArrayList<String>(attach);
		for(String s : list) {
			if(isValidString(s) == false)
				return false;
		}
		return true;
	}
}
