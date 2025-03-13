package com.example.demo.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.demo.controller.RoleController;
import com.example.demo.model.DefectModel;
import com.example.demo.model.RoleModel;
import com.example.demo.model.ServiceModel;
import com.example.demo.model.UserModel;
import com.example.demo.services.JwtTokenUtil;

@Component
public class RoleValidation {

	@Autowired 
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	public String getId(String token) {
		String tk = token.substring(7);
		return jwtTokenUtil.getUsernameFromToken(tk);
	}
	
	public HashMap<String, Object> error() {
		HashMap<String, Object> hm =new HashMap<String, Object>();
		hm.put("Error","You are not authorized to use this Service.");
		logger.error("UnAuthorized user trying to access Service.");
		return hm;
	}
	
	
	public boolean isValidUser(String eid, String service) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(eid));
		query.addCriteria(Criteria.where("userStatus").is("active"));
		UserModel user =  mongoTemplate.findOne(query,UserModel.class,"UserDetails");
		if(user==null)
			return false;
		Set<String> userRoles = user.getUserRoles();
		
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("serviceName").is(service));
		ServiceModel ser =  mongoTemplate.findOne(query1,ServiceModel.class,"Services");
		Set<String> serRoles = ser.getRolesPermission();
		
		for(String u : userRoles) {
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("roleName").is(u));
			RoleModel roles =  mongoTemplate.findOne(query2,RoleModel.class,"Roles");
			if(roles!=null && (roles.getRoleStatus().equals("active"))) {
				if(serRoles.contains(u)) {
					return true;
				}
					
			}
		}
		return false;
	}
}
