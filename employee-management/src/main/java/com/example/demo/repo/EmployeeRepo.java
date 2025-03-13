package com.example.demo.repo;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.model;

@Repository
public interface EmployeeRepo extends MongoRepository<model,Long> {

	

}
