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

import com.example.demo.model.Project;
import com.example.demo.model.Requirement;
import com.example.demo.model.Testcase;
import com.example.demo.repo.ProjectRepo;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.SequenceGenerator;
import com.example.demo.repo.TestcaseRepo;

@RestController
@RequestMapping("/api/v1/TestcaseService")
public class TestcaseController {

	@Autowired
	public TestcaseRepo trepo;

	@Autowired
	ProjectRepo prepo;

	@Autowired
	SequenceGenerator seq;

	@Autowired
	RoleValidation valid;

	Logger logger = LoggerFactory.getLogger(ProjectController.class);

	public boolean notValidInput(String field) {
		if (field == null || field.trim() == "")
			return true;
		return false;
	}

	// Add a new Testcase
	@PostMapping("/addTestcase")
	public HashMap<String, Object> addTestcase(@RequestBody Testcase t,
			@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: POST ] Add new Testcase Started");
		HashMap<String, Object> data = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "addTestcase")) {
			int err = 0;
			if (notValidInput(t.getProjectId()) || prepo.getProjectByPid(t.getProjectId()) == null) {
				data.put("Error", "Invalid Project ID");
				logger.error("Can't add Testcase: Invalid Project ID");
				return data;
			}
			List<Requirement> reqs = prepo.getProjectByPid(t.getProjectId()).getRequirements();
			boolean present = false;
			if (notValidInput(t.getRequirementId())) {
				data.put("Error", "Invalid Requirement ID");
				logger.error("Can't add Testcase: Invalid Requirement ID");
				err += 1;
				return data;
			}
			for (Requirement r : reqs) {
				if (r.getRequirementNo().equals(t.getRequirementId())) {
					present = true;
					break;
				}
			}
			if (present == false) {
				data.put("Error", "Invalid Requirement ID");
				logger.error("Can't add Testcase: Invalid Requirement ID");
				err += 1;
				return data;
			}
			if (notValidInput(t.getDescription())) {
				data.put("Error", "Invalid Description");
				logger.error("Can't add Testcase: Invalid Description");
				err += 1;
			}
			if (notValidInput(t.getExpectedResults())) {
				data.put("Error", "Invalid Expected Result");
				logger.error("Can't add Testcase: Invalid Expected Result");
				err += 1;
			}
			if (notValidInput(t.getActualResults())) {
				data.put("Error", "Invalid Actual Results");
				logger.error("Can't add Testcase: Invalid Actual Results");
				err += 1;
			}
			if (err > 0)
				return data;
			String tid = "T" + seq.generateSequenceforTestcase();
			t.setTestcaseId(tid);
			t.setStatus("Active");
			t.setLastUpdatedDate(new Date());
			t.setUpdatedBy(valid.getId(token));
			t.setCreatedBy(valid.getId(token));
			Testcase temp = trepo.save(t);
			data.put("Added Testcase", temp);
			logger.info("Added Testcase Successfully");
			return data;
		}
		return valid.error();

	}

	// Get all Testcases
	@GetMapping("/getTestcases")
	public HashMap<String, Object> getAllTestcases(@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: GET ] Get All Testcases Started");
		HashMap<String, Object> hm = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "getTestcases")) {
			List<Testcase> temp = trepo.getAlltestcases();
			if (temp == null || temp.size()==0) {
				hm.put("Response", "No Testcases");
				logger.info("No Testcases");
				return hm;
			}
			logger.info("Testcases fetch Successful");
			hm.put("Testcases", temp);
			return hm;
		}
		return valid.error();
	}

	// Get all Testcases by Requirement ID
	@GetMapping("/getTestcasesByRid/{rid}")
	public HashMap<String, Object> getAllTestcasesByRid(@PathVariable(value = "rid") String rid,
			@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: GET ] Get Testcases By Requirement ID Started");
		HashMap<String, Object> hm = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "getTestcasesByRid")) {
			List<Testcase> temp = trepo.getAlltestcasesByRid(rid);
			hm.put("Testcases", temp);
			if (temp == null || temp.size()==0) {
				hm.put("Response", "No Testcases");
				logger.info("No Testcasess");
				return hm;
			}
			logger.info("Testcases fetch Successful");
			hm.put("Testcases", temp);
			return hm;
		}
		return valid.error();
	}

	// Get all Testcases by Project ID
	@GetMapping("/getTestcasesByPid/{pid}")
	public HashMap<String, Object> getAllTestcasesByPid(@PathVariable(value = "pid") String pid,
			@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: GET ] Get Testcases By Project ID Started");
		HashMap<String, Object> hm = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "getTestcasesByPid")) {
			List<Testcase> temp = trepo.getAlltestcasesByPid(pid);
			hm.put("Testcases", temp);
			if (temp == null || temp.size()==0) {
				hm.put("Response", "No Testcases");
				logger.info("No Testcases");
				return hm;
			}
			logger.info("Testcases fetch Successful");
			hm.put("Testcases", temp);
			return hm;
		}
		return valid.error();
	}

	// Update Testcase
	@PatchMapping("/editTestcase/{tid}")
	public HashMap<String, Object> editTestcase(@PathVariable(value = "tid") String tid, @RequestBody Testcase update,
			@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: PATCH ] Edit Testcases By Project ID Started");
		HashMap<String, Object> hm = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "editTestcase")) {
			String eid = valid.getId(token);
			Testcase temp = trepo.editTestcase(tid, update, eid);
			if (temp == null) {
				hm.put("Response", "No Testcases");
				logger.info("No Testcases");
				return hm;
			}
			logger.info("Testcase Update Successful");
			hm.put("Edited Testcase", temp);
			return hm;
		}
		return valid.error();
	}

	// Delete Testcase
	@DeleteMapping("/deleteTestcase/{tid}")
	public HashMap<String, Object> deleteTestcase(@PathVariable(value = "tid") String tid,
			@RequestHeader(name = "Authorization") String token) {
		logger.info("[ Service: DELETE ] Delete Testcases By Testcase ID Started");
		HashMap<String, Object> hm = new HashMap<>();
		if (valid.isValidUser(valid.getId(token), "deleteTestcase")) {
			String eid = valid.getId(token);
			Testcase temp = trepo.deleteTestcase(tid, eid);
			if (temp == null) {
				hm.put("Response", "No Testcases");
				logger.info("No Testcases");
				return hm;
			}
			logger.info("Testcase Deleted Successfully");
			hm.put("Deleted Testcase", temp);
			return hm;
		}
		return valid.error();
	}
}
