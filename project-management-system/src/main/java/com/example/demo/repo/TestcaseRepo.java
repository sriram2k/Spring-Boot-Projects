package com.example.demo.repo;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Testcase;

@Repository
public class TestcaseRepo {
	
	@Autowired
	MongoTemplate mongoTemplate;

	//Add a new Testcase
	public Testcase save(Testcase t) {
		return mongoTemplate.save(t);
	}

	//Get all Testcases
	public List<Testcase> getAlltestcases() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("status").ne("InActive");
		return mongoTemplate.find(query,Testcase.class,"Testcases");
	}

	//Get all Testcases  by Requirement ID
	public List<Testcase> getAlltestcasesByRid(String rid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("requirementId").is(rid);
		criteria.and("status").ne("InActive");
		query.addCriteria(criteria);
		List<Testcase> d = mongoTemplate.find(query, Testcase.class,"Testcases");
		return d;
	}
	//Get all Testcases by Project ID
	public List<Testcase> getAlltestcasesByPid(String pid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("projectId").is(pid);
		criteria.and("status").ne("InActive");
		query.addCriteria(criteria);
		List<Testcase> d = mongoTemplate.find(query, Testcase.class,"Testcases");
		return d;
	}

	
	//Update Testcase
	public Testcase editTestcase(String tid, Testcase update, String eid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("testcaseId").is(tid);
		query.addCriteria(criteria);
		Testcase t = mongoTemplate.findOne(query, Testcase.class,"Testcases");
		if(t==null)
			return null;
		t.setLastUpdatedDate(new Date());
		t.setUpdatedBy(eid);
		if(update.getExpectedResults()!=null)
			t.setExpectedResults(update.getExpectedResults());
		if(update.getActualResults()!=null)
			t.setActualResults(update.getActualResults());
		if(update.getDescription()!=null)
			t.setDescription(update.getDescription());
		if(update.getStatus()!=null)
			t.setStatus(update.getStatus());
		if(update.getTestcaseName()!=null)
			t.setTestcaseName(update.getTestcaseName());
		return mongoTemplate.save(t,"Testcases");
	}
	
	
	//Delete Testcase
	public Testcase deleteTestcase(String tid, String eid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("testcaseId").is(tid);
		query.addCriteria(criteria);
		Testcase t = mongoTemplate.findOne(query, Testcase.class,"Testcases");
		if(t==null)
			return null;
		t.setLastUpdatedDate(new Date());
		t.setUpdatedBy(eid);
		t.setStatus("InActive");
		return mongoTemplate.save(t,"Testcases");
	}
	
	public Testcase getTestcaseByTestcaseId(String tid) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("testcaseId").is(tid);
		query.addCriteria(criteria);
		Testcase t = mongoTemplate.findOne(query, Testcase.class,"Testcases");
		return t;
	}
}
