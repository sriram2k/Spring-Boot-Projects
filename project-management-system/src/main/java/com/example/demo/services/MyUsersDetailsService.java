package com.example.demo.services;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserModel;

@Repository
@Service
public class MyUsersDetailsService implements UserDetailsService{
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(username));
		UserModel u = mongoTemplate.findOne(query, UserModel.class, "UserDetails");
		return new User(username,u.getPassword(),new ArrayList<>());
	}
	

}
