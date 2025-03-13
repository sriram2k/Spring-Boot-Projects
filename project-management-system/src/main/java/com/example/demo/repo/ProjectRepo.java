package com.example.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Project;
import com.example.demo.model.Requirement;
import com.example.demo.model.Testcase;

@Repository
public class ProjectRepo {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	TestcaseRepo trepo;
	
	// Adding a new Project
	public Project saveProject(Project p) {
		return mongoTemplate.save(p, "Projects");
	}

	//Getting all the Active projects Present in the Database
	public List<Project> getAllProjects() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is("Active"));
		List<Project> p = mongoTemplate.find(query,Project.class, "Projects");
		return p;
	}
	
	//Getting a particular Project with Pid 
	public Project getProjectByPid(String pid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectId").is(pid));
		return mongoTemplate.findOne(query, Project.class, "Projects");
	}

	//Updating fields in the Project
	public Project updateProject(Project p, String pid, String eid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectId").is(pid));
		Project pr = mongoTemplate.findOne(query, Project.class, "Projects");
		if(pr==null || pr.getStatus().equalsIgnoreCase("InActive"))
			return null;
		pr.setUpdatedBy(eid);
		pr.setLastUpdatedDate(new Date());
		if (p.getStartDate() != null)
			pr.setStartDate(p.getStartDate());
		if (p.getEndDate() != null)
			pr.setEndDate(p.getEndDate());
		if (p.getProjectDescription() != null)
			pr.setProjectDescription(p.getProjectDescription());
		if (p.getProjectName() != null)
			pr.setProjectName(p.getProjectName());
		if (p.getTargetedRelease() != null)
			pr.setTargetedRelease(p.getTargetedRelease());
		if(p.getStatus()!=null)
			pr.setStatus(p.getStatus());
		return mongoTemplate.save(pr, "Projects");
	}

	//Deleting a Project 
	public Project deleteProject(String pid, String eid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectId").is(pid));
		Project pr = mongoTemplate.findOne(query, Project.class, "Projects");
		if(pr==null)
			return null;
		pr.setUpdatedBy(eid);
		pr.setLastUpdatedDate(new Date());
		pr.setStatus("InActive");
		List<Testcase> testcases = trepo.getAlltestcasesByPid(pid);
		for(Testcase test: testcases) {
			test.setStatus("InActive");
			trepo.editTestcase(test.getTestcaseId(), test, eid);
		}
		for (Requirement p : pr.getRequirements()) {
			p.setStatus("InActive");
		}
		mongoTemplate.save(pr, "Projects");
		return pr;
	}

	//Updating Requirement of a particular Project
	public Project updateRequirement(String rid, Requirement r, String eid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		String pid = rid.substring(0, rid.indexOf('R'));
		criteria.and("projectId").is(pid);
		query.addCriteria(criteria);
		Project fp = mongoTemplate.findOne(query, Project.class, "Projects");
		if(fp==null || fp.getStatus().equalsIgnoreCase("InActive"))
			return null;
		fp.setUpdatedBy(eid);
		fp.setLastUpdatedDate(new Date());
		for (Requirement p : fp.getRequirements()) {
			if (p.getRequirementNo().equals(rid)) {
				if(p.getStatus().equalsIgnoreCase("InActive"))
					return null;
				if (r.getStatus() != null)
					p.setStatus(r.getStatus());
				if (r.getRequirementDescription() != null)
					p.setRequirementDescription(r.getRequirementDescription());
			}
		}
		return mongoTemplate.save(fp, "Projects");
	}

	//Adding a Requirement to a particular Project
	public Project addRequirement(Requirement r, String pid, String eid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("projectId").is(pid);
		query.addCriteria(criteria);
		Project fp = mongoTemplate.findOne(query, Project.class, "Projects");
		if(fp==null|| fp.getStatus().equalsIgnoreCase("InActive"))
			return null;
		fp.setUpdatedBy(eid);
		fp.setLastUpdatedDate(new Date());
		List<Requirement> newRequirements = fp.getRequirements();
		r.setStatus("New");
		r.setRequirementNo(pid+"R"+(newRequirements.size()+1));
		newRequirements.add(r);
		fp.setRequirements(newRequirements);
		return mongoTemplate.save(fp, "Projects");
	}

	//deleting a Requirement for a particular Project
	public Project deleteRequirement(String rid, String eid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		String pid = rid.substring(0, rid.indexOf('R'));
		criteria.and("projectId").is(pid);
		query.addCriteria(criteria);
		Project fp = mongoTemplate.findOne(query, Project.class, "Projects");
		if(fp==null|| fp.getStatus().equalsIgnoreCase("InActive"))
			return null;
		fp.setUpdatedBy(eid);
		fp.setLastUpdatedDate(new Date());
		for (Requirement p : fp.getRequirements()) {
			if (p.getRequirementNo().equals(rid)) {
				p.setStatus("InActive");
				List<Testcase> testcases = trepo.getAlltestcasesByRid(rid);
				for(Testcase test: testcases) {
					test.setStatus("InActive");
					trepo.editTestcase(test.getTestcaseId(), test, eid);
				}
				break;
			}
		}
		return mongoTemplate.save(fp, "Projects");
	}
}
