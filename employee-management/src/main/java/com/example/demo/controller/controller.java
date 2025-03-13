package com.example.demo.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.*;
import com.example.demo.repo.EmployeeRepo;



@RestController
@RequestMapping("/api/v1/")
public class controller {

	@Autowired
	private EmployeeRepo erepo;
	
	@PostMapping("/Employee")
	public model createEmployee(@RequestBody model emp){
		return erepo.save(emp);
		
	}
	
	@GetMapping("/Employee/{id}")
	public ResponseEntity<model> getEmployeeById(@PathVariable(value="id") Long empID) {
		model m= erepo.findById(empID).get() ;
		//System.out.println();
		return ResponseEntity.ok().body(m) ;
		
	}
	
	@GetMapping("/Employees")
	public ResponseEntity<Iterable<model>> getAllEmployee() {
		Iterable<model> iterable = erepo.findAll();
	      for (model employee : iterable) {
	          System.out.println(employee);
	      }
		return ResponseEntity.ok().body(iterable) ;
		
	}
	
	@PutMapping("/Employee/{eid}")
	public ResponseEntity<String> getEmployeeById(@PathVariable(value="eid") Long empID,@RequestBody model empdetails) {
		model m= erepo.findById(empID).get() ;
		m.setFirstname(empdetails.getFirstname());
		m.setLastname(empdetails.getLastname());
		m.setBranch(empdetails.getBranch());
		final model updateModel = erepo.save(m);
		return ResponseEntity.ok(updateModel.toString());
		
	}
	
	@DeleteMapping("/Employee/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable(value="id") Long empID) {
		model m= erepo.findById(empID).get() ;
		erepo.delete(m);
		Map<String,Boolean> response = new HashMap<>();
		response.put("Deleted",true) ;
		return response;
	}
}
