package com.example.demo.repo;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.demo.model.ServiceModel;
import com.example.demo.model.UserModel;
import com.mongodb.client.result.DeleteResult;

@Component
public class ServiceRepo {

	@Autowired
	MongoTemplate mongoTemplate;
	
	Logger logger = LoggerFactory.getLogger(ServiceRepo.class);
	
	// This inserts a service into database.
	public HashMap<String, Object> save(ServiceModel um,String userid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		um.setCreatedBy(userid);
		um.setLastUpdatedBy(userid);
		um.setLastUpdatedDate(new Date());
		hm.put("Inserted", mongoTemplate.save(um));
		return hm;
	}
	
	//This fetches all the services and its permissions from the database.
	public HashMap<String, Object> findAll() {
		List<ServiceModel> models = new ArrayList<>();
		models = mongoTemplate.findAll(ServiceModel.class);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		if(models.size()==0) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Services", models);
		return hm;
	}
	
	// This fetches the service details and its allowed permissions by the role name.
	public HashMap<String, Object> findByRole(String role) {
		Query query = new Query();
		List<ServiceModel> models = new ArrayList<>();
		query.addCriteria(Criteria.where("rolesPermission").is(role));
		models = mongoTemplate.find(query,ServiceModel.class,"Services");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		if(models.size()==0) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("Services", models);
        return hm;
	}
	
	// This fetches the service details and its allowed permissions by the service name.
	public HashMap<String, Object> findByService(String servicename) {
		Query query = new Query();
		query.addCriteria(Criteria.where("serviceName").is(servicename));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		ServiceModel models =mongoTemplate.findOne(query, ServiceModel.class);
		if(models==null) {
			logger.info("No reocrds found");
			hm.put("Error"," No records found." );
			return hm;
		}
		hm.put("ServiceDetail", models);
        return hm;
	}
	
	// This provides the permission for a service by a specific service name.
	public HashMap<String, Object> updatePermissions(ServiceModel p, String servicename,String userid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("serviceName").is(servicename));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		ServiceModel pr = mongoTemplate.findOne(query, ServiceModel.class, "Services");
		if(pr==null) {
			logger.info("No reocrds found. Please Enter a valid service name");
			hm.put("Error"," No records found. Please Enter a valid service name" );
			return hm;
		}
		Set<String> s= pr.getRolesPermission();
		s.addAll(p.getRolesPermission());
		if(p.getRolesPermission()!=null)
			pr.setRolesPermission(s);
		pr.setLastUpdatedBy(userid);
		pr.setLastUpdatedDate(new Date());
		
		hm.put("Updated", mongoTemplate.save(pr, "Services"));
		return hm;
	}

	// This removes the permission for a particular service. 
	public HashMap<String, Object> removePermissions(ServiceModel p, String servicename,String userid) {
		Query query = new Query();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		query.addCriteria(Criteria.where("serviceName").is(servicename));
		ServiceModel pr = mongoTemplate.findOne(query, ServiceModel.class, "Services");
		if(pr==null) {
			logger.info("No reocrds found. Please Enter a valid service name");
			hm.put("Error"," No records found. Please Enter a valid service name" );
			return hm;
		}
		Set<String> s= pr.getRolesPermission();
		s.removeAll(p.getRolesPermission());
		if(p.getRolesPermission()!=null)
			pr.setRolesPermission(s);
		pr.setLastUpdatedBy(userid);
		pr.setLastUpdatedDate(new Date());
		
		hm.put("Updated", mongoTemplate.save(pr, "Services"));
		return hm;
	}
	
//  This remove() method deletes a service from the database.
	public HashMap<String, Object> deleteService(String sname) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		DeleteResult sm= mongoTemplate.remove(new Query(Criteria.where("serviceName").is(sname)), ServiceModel.class);
		if(sm.getDeletedCount()==0) {
			logger.info("No reocrds found. Please Enter a valid service name to be deleted");
			hm.put("Error"," No records found. Please Enter a valid service name to be deleted" );
			return hm;
		}
		hm.put("Deleted",sm );
		return hm;
	}

}
