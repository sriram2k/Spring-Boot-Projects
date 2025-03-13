package com.example.demo.repo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


import com.example.demo.model.RoleModel;
import com.example.demo.model.ServiceModel;
import com.example.demo.model.UserModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class RoleRepo {

	@Autowired
	MongoTemplate mongoTemplate;
	
	MongoOperations mongoOperation;
	
	Logger logger = LoggerFactory.getLogger(RoleRepo.class);

	// This method inserts the record into the database.
	public HashMap<String, Object> save(RoleModel um,String userid) {
		HashMap<String, Object> hm =new HashMap<String, Object>();
		um.setCreatedBy(userid);
		um.setRoleStatus("active");
		um.setLastUpdatedBy(userid);
		um.setLastUpdatedDate(new Date());
		hm.put("Inserted", mongoTemplate.save(um));
		return hm;
	}
	
	// findAll() method fetches all the records from the database and stores in the List.
	public HashMap<String, Object> findAll() {
		Query query = new Query();
		query.addCriteria(Criteria.where("roleStatus").is("active"));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		List<RoleModel> rm = mongoTemplate.find(query, RoleModel.class);
		if(rm.size()==0) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Roles", rm);
        return hm;
	}
	
	//findOne() method fetches one record with respective to the role id. 
	public HashMap<String, Object> findOne(String roleid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(roleid));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		RoleModel rm= mongoTemplate.findOne(query, RoleModel.class);
		if(rm==null) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("RoleDetail",rm);
        return hm;
	}
	
	
	//  This method deletes a record from the database.
	public HashMap<String, Object> deleteOne(String roleid,String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(roleid));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		RoleModel um = mongoTemplate.findOne(query, RoleModel.class);
		if(um==null) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		um.setRoleStatus("inactive");
		um.setLastUpdatedBy(userid);
		um.setLastUpdatedDate(new Date());
		hm.put("Deleted", mongoTemplate.save(um));
		return hm;
//		return mongoTemplate.remove(new Query(Criteria.where("_id").is(userid)), UserModel.class);
	}
	
	// This updates the particular record with the query criteria 
	public HashMap<String, Object> updateOneRole(RoleModel p, String uid,String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("roleId").is(uid));
		RoleModel pr = mongoTemplate.findOne(query, RoleModel.class, "Roles");
		HashMap<String, Object> hm =new HashMap<String, Object>();
			
		pr.setRoleName(p.getRoleName());
		pr.setLastUpdatedBy(userid);
		pr.setLastUpdatedDate(new Date());
		
		hm.put("Updated", mongoTemplate.save(pr, "Roles"));
		return hm;
	}
	
	
	

}
