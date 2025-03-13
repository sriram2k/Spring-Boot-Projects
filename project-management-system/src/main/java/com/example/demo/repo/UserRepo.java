package com.example.demo.repo;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.demo.controller.RoleController;
import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class UserRepo {

	@Autowired
	MongoTemplate mongoTemplate;
	
	MongoOperations mongoOperation;

	Logger logger = LoggerFactory.getLogger(UserRepo.class);
	
	// This method inserts the user record into the database. 
	public HashMap<String,Object> save(UserModel um,String userid) {
		
		HashMap<String,Object> hash =new HashMap<String,Object>();
		um.setCreatedBy(userid);
		um.setUserStatus("active");
		um.setLastUpdatedBy(userid);
		um.setLastUpdatedDate(new Date());
		UserModel userm = mongoTemplate.save(um);
		hash.put("data",userm);
		
		return hash ;
	}
	
	// finaAll() method fetches all the user records from the database and stores in the List.
	public HashMap<String, Object> findAll() {
		Query query = new Query();
		query.addCriteria(Criteria.where("userStatus").is("active"));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		List<UserModel> um =mongoTemplate.find(query, UserModel.class);
		if(um.size()==0) {
			logger.info("No records found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Users",um);
		return hm;
	}
	
	//findOne() method fetches one record with respective to the role id.
	public HashMap<String, Object> findOne(String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(userid));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		UserModel um =mongoTemplate.findOne(query, UserModel.class);
		if(um==null) {
			logger.info("No records found");
			hm.put("Error"," No records found." );
			return hm;
		}
        hm.put("UserDetail", um);
        return hm;
	}

	// This method fetches all the user records from the database for a particular role and stores in the List.
	public HashMap<String, Object> findAllRoles(String rolename) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userRoles").is(rolename));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		UserModel um =mongoTemplate.findOne(query, UserModel.class);
		if(um==null) {
			logger.info("No records found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Users",um);
		return hm;
	}
	
	// This method fetches all the user records from the database for a particular project and stores in the List.
	public HashMap<String, Object> findAllProjects(String pid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectId").is(pid));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		UserModel um =mongoTemplate.findOne(query, UserModel.class);
		if(um==null) {
			logger.info("No records found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Users",um);
		return hm;
	}
	
	// This updates the particular record with the query criteria. 
	public HashMap<String, Object> updateOneUser(UserModel p, String uid,String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(uid));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		UserModel pr = mongoTemplate.findOne(query, UserModel.class, "UserDetails");
		if(pr==null) {
			logger.info("No records found");
			hm.put("Error","No records found." );
			return hm;
		}
		Set<String> s= pr.getUserRoles();
		s.addAll(p.getUserRoles());
		
		if(p.getProjectId()!=null)
			pr.setProjectId(p.getProjectId());
		if(p.getUsername()!=null)
			pr.setUsername(p.getUsername());
		if(p.getPassword()!=null)
			pr.setPassword(p.getPassword());
		if(p.getFirstName()!=null)
			pr.setFirstName(p.getFirstName());
		if(p.getLastName()!=null)
			pr.setLastName(p.getLastName());
		if(p.getEmailId()!=null)
			pr.setEmailId(p.getEmailId());
		if(p.getMobileNumber()!=null)
			pr.setMobileNumber(p.getMobileNumber());
		if(p.getDateOfJoining()!=null)
			pr.setDateOfJoining(p.getDateOfJoining());
		if(p.getDateOfResigning()!=null)
			pr.setDateOfResigning(p.getDateOfResigning());
		if(p.getUserRoles()!=null)
			pr.setUserRoles(s);
		
		pr.setLastUpdatedBy(userid);
		pr.setLastUpdatedDate(new Date());
		
		hm.put("Updated",  mongoTemplate.save(pr, "UserDetails"));
		return hm;
	}
	
//  This remove() method deletes a user record from the database.
	public HashMap<String, Object> deleteOne(String userid,String uid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(userid));
		HashMap<String, Object> hm =new HashMap<String, Object>();
		UserModel um = mongoTemplate.findOne(query, UserModel.class);
		if(um==null) {
			logger.info("No records found");
			hm.put("Error"," No records found." );
			return hm;
		}
		um.setUserStatus("inactive");
		um.setLastUpdatedBy(uid);
		um.setLastUpdatedDate(new Date());
		
		hm.put("Deleted",  mongoTemplate.save(um));
		return hm;
//		return mongoTemplate.remove(new Query(Criteria.where("_id").is(userid)), UserModel.class);
	}
	
	
	

}
