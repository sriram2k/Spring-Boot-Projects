package com.example.demo.repo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import com.example.demo.model.DefectModel;
import com.example.demo.model.DefectRunningTrend;
import com.example.demo.model.ProjectRunningTrend;
import com.example.demo.model.Testcase;
import com.sun.el.parser.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale; 

@Service
public class Scheduler {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public String TodayDate() {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    String strDate = formatter.format(date);  
	    return strDate;
		
	}
	
	//@Scheduled(cron = "0 0 10 * * ?") // 10 am	
	//@Scheduled(cron = "0 40 16 * * ?")
	
	public int ActiveDefectCount() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("defectStatus").is("active");
		query.addCriteria(criteria);
		List<DefectModel> list = mongoTemplate.find(query, DefectModel.class);
		return list.size();
	}
	
	public int ActiveTestCaseCount() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("status").ne("InActive");
		query.addCriteria(criteria);
		List<Testcase> list = mongoTemplate.find(query, Testcase.class);
		return list.size();
	}
	
	//@Scheduled(cron = "0 26 17 * * ?")
	@Scheduled(cron = "0 0 10 * * ?") // 10 am
	public void countDefectsAtMorning() {
		DefectRunningTrend dr = new DefectRunningTrend();
		dr.setDate(TodayDate());
		dr.setMorningCount(ActiveDefectCount());
		mongoTemplate.save(dr);
		
		ProjectRunningTrend pr = new ProjectRunningTrend();
		pr.setDate(TodayDate());
		pr.setMorningCount(ActiveTestCaseCount());
		mongoTemplate.save(pr);
		
	}
	
	//@Scheduled(cron = "0 27 17 * * ?")
	@Scheduled(cron = "0 0 18 * * ?") // 6 pm
	public void countDefectsAtEvening() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("date").is(TodayDate());
		query.addCriteria(criteria);
		DefectRunningTrend trend = mongoTemplate.findOne(query, DefectRunningTrend.class);
		trend.setEveningCount(ActiveDefectCount());
		mongoTemplate.save(trend);
		
		Query q = new Query();
		Criteria c = new Criteria();
		c.and("date").is(TodayDate());
		q.addCriteria(criteria);
		ProjectRunningTrend tr = mongoTemplate.findOne(q, ProjectRunningTrend.class);
		tr.setEveningCount(ActiveTestCaseCount());
		mongoTemplate.save(tr);
		
	}
}
