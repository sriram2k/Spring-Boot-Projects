package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DefectDashboard;
import com.example.demo.model.Project;
import com.example.demo.model.RTM;
import com.example.demo.model.Requirement;
import com.example.demo.model.Testcase;
import com.example.demo.repo.DefectRepo;
import com.example.demo.repo.ProjectRepo;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.Scheduler;
import com.example.demo.repo.TestcaseRepo;

@RestController
@RequestMapping("/api/v1/Dashboard/")
public class DashboardController {
	

	@Autowired
	private DefectRepo drepo;
	
	@Autowired
	RoleValidation valid;
	
	@Autowired
	TestcaseRepo trepo;
	
	@Autowired
	ProjectRepo prepo;
	
	// Defect Dashboard Services
	@GetMapping("/Defects")
	public  HashMap<String, Object> getDefects( @RequestHeader (name="Authorization") String token) {
        if(valid.isValidUser(valid.getId(token), "displayDefects")) {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	DefectDashboard d = drepo.defectDashboard();
			map.put("Defect Dashboard : ", d);
			return map;
        }
        else {
        	return valid.error();
        }
	}
	
	// Dashboard Service for Project
	@GetMapping("/getRTM/{pid}")
	public HashMap<String, Object> getRTM(@PathVariable(value = "pid") String pid) {
		HashMap<String,Object> hm = new HashMap<>();
		Project pr = prepo.getProjectByPid(pid);
		if(pr==null) {
			hm.put("Response", "No projects Found");
			return hm;
		}
		
		List<RTM> Rtm = new ArrayList<>();
		List<Requirement> t = pr.getRequirements();
		for(Requirement r : t) {
			RTM newrtm = new RTM();
			List<String> tids = new ArrayList<>();
			newrtm.setRequirementId(r.getRequirementNo());
			List<Testcase> testcases = trepo.getAlltestcasesByRid(r.getRequirementNo());
			int total = testcases.size();
			int count = 0;
			for(Testcase g : testcases) {
				if(g.getStatus().equalsIgnoreCase("closed"))
					count+=1;
				tids.add(g.getTestcaseId());
			}
			newrtm.setTestcases(tids);
			if(total==0)
				newrtm.setStatus("Not Started");
			else if(count<total)
				newrtm.setStatus("Partial");
			else
				newrtm.setStatus("Completed");
			Rtm.add(newrtm);
		}
		hm.put("RTM for Project: "+pid, Rtm);
		return hm;
	}

}
