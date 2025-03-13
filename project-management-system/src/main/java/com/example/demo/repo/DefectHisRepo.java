package com.example.demo.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.demo.model.CommentDesModel;
import com.example.demo.model.CommentModel;
import com.example.demo.model.DefectHisModel;
import com.example.demo.model.DefectModel;
import com.example.demo.model.History;


@Component
public class DefectHisRepo {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	// Adding new Defect History - okay
	public void addDefectHistory(DefectModel defect) {
		
		DefectHisModel defectHis = new DefectHisModel();
		defectHis.setDefectId(defect.getDefectId());
		defectHis.setDefectStatus(defect.getDefectStatus());
		History his = new History();
		his.setEventId(1);
		his.setStatus(defect.getBugStatus());
		his.setStartDate(new Date());
		his.setWorkDoneBy(defect.getAssignedTo());
		ArrayList<History> list = new ArrayList<History>();
		list.add(his);
		defectHis.setDefectHistory(list);
		mongoTemplate.save(defectHis);
	}



	// Displaying all active defect history
	public List<DefectHisModel> findAll() {
		List<DefectHisModel> defect = null;
		try {

	        Query query = new Query();
			Criteria criteria = new Criteria();
			// Fetching all active records
			criteria.and("defectStatus").is("active");
			query.addCriteria(criteria);
			defect=mongoTemplate.find(query, DefectHisModel.class);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return defect;
	}

	// Adding history to defect history in History model format
	public void updateDefectHisAssignedTo(String defectId, DefectModel defect, String user) {
		
		DefectHisModel defectHis = findDefectHistoryByDefectId(defectId);
		History his = new History();
		his.setStartDate(new Date());
		his.setEventId(defectHis.getDefectHistory().size() + 1);
		int size = defectHis.getDefectHistory().size();
		defectHis.getDefectHistory().get(--size).setEndDate(new Date());
		// Setting endDate and startDate
		his.setStatus(defect.getBugStatus());
		// updating assigned To in defect history
		his.setWorkDoneBy(defect.getAssignedTo());
		defectHis.getDefectHistory().add(his);
		mongoTemplate.save(defectHis);
	}

	
	public void updateDefectHisBug(String defectId, DefectModel defect, String user) {
		
		DefectHisModel defectHis = findDefectHistoryByDefectId(defectId);
		History his = new History();
		
		his.setStartDate(new Date());
		his.setEventId(defectHis.getDefectHistory().size() + 1);
		int size = defectHis.getDefectHistory().size();
		defectHis.getDefectHistory().get(--size).setEndDate(new Date());
		// updating bug status is defect history
		his.setStatus(defect.getBugStatus());
		his.setWorkDoneBy(user);
		defectHis.getDefectHistory().add(his);
		mongoTemplate.save(defectHis);
	}
	
	// Closing defect History ( updating end date )
	public DefectHisModel closeDefectHistory(String defectId, String user) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		defectId = Character.toUpperCase(defectId.charAt(0)) + defectId.substring(1);
		criteria.and("defectId").is(defectId);
		query.addCriteria(criteria);
		DefectHisModel defectHis = mongoTemplate.findOne(query, DefectHisModel.class);
		if(defectHis!=null) {
			int size = defectHis.getDefectHistory().size();
			int sz = size-1;
			defectHis.getDefectHistory().get(sz).setEndDate(new Date());
			defectHis.getDefectHistory().get(sz).setWorkDoneBy(user);
			return defectHis;
		}
		return null;
	}


	
	// Displaying defect history of defect id
	public DefectHisModel findDefectHistoryByDefectId(String defectId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		defectId = Character.toUpperCase(defectId.charAt(0)) + defectId.substring(1);
		criteria.and("defectId").is(defectId);
		query.addCriteria(criteria);
		DefectHisModel defectHis = mongoTemplate.findOne(query, DefectHisModel.class);
		return defectHis;
	}

/*	
	// Removing defect history
	public String removeDefectHisByDefectId(String defectId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("defectId").is(defectId);
		query.addCriteria(criteria);
		DefectHisModel defectHis = mongoTemplate.findOne(query, DefectHisModel.class);
		defectHis.setDefectStatus("closed");
		mongoTemplate.save(defectHis);
		return "Defect History Removed Successfully";
	}
	
*/	
	
	
}
