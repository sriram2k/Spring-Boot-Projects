package com.example.demo.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.demo.controller.RoleController;
import com.example.demo.model.CommentDesModel;
import com.example.demo.model.CommentModel;
import com.example.demo.model.DefectModel;
import com.mongodb.client.result.DeleteResult;

@Component
public class CommentRepo {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	// Creating new Comment
	public void addComment(DefectModel defect) {
		CommentModel comment = new CommentModel();
		comment.setDefectId(defect.getDefectId());
		comment.setDefectStatus(defect.getDefectStatus());
		CommentDesModel cm = new CommentDesModel();
		cm.setDate(new Date());
		cm.setCommentId("C1");
		cm.setRepliedBy(defect.getCreatedBy());
		cm.setRepliedTo(defect.getAssignedTo());
		cm.setComments(defect.getExpectedResults());
		// Adding chat comments in it
		ArrayList<CommentDesModel> l = new ArrayList<CommentDesModel>();
		l.add(cm);
		comment.setCommentDescription(l);
		mongoTemplate.save(comment);	
		
	}
	
	
	// Display all comments with active defects.
	public List<CommentModel> findAll() {
		List<CommentModel> comment = null;
		try {

	        Query query = new Query();
			Criteria criteria = new Criteria();
			// Fetching active records
			criteria.and("defectStatus").is("active");
			query.addCriteria(criteria);
			comment=mongoTemplate.find(query, CommentModel.class);
		}
		catch(Exception e){
			System.out.println(e);
			logger.error("Comment not found");
		}
		return comment;
	}
	
	// add chats or replies to particular comment. This can be done for active defects.
	public CommentModel addChat(String defectId, CommentDesModel chat, String user) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("defectId").is(defectId);
		defectId = Character.toUpperCase(defectId.charAt(0)) + defectId.substring(0);
		// Fetching active records
		criteria.and("defectStatus").is("active");
		query.addCriteria(criteria);
		CommentModel comment = mongoTemplate.findOne(query, CommentModel.class);
		if(comment!=null) {
			List<CommentDesModel> sub = comment.getCommentDescription();
			int value = sub.size();
			value++;
			chat.setCommentId("C" + value);
			chat.setRepliedBy(user);
			chat.setDate(new Date());
			sub.add(chat);
			
			return mongoTemplate.save(comment);
		}
		return null;
	}

	// Display comments of particular active defect Id 
	public CommentModel findCommentByDefectId(String defectId) {
		CommentModel comment = null;
		try {
			Query query=new Query();
			Criteria criteria = new Criteria();
			defectId = Character.toUpperCase(defectId.charAt(0)) + defectId.substring(1);
			criteria.and("defectId").is(defectId);
			query.addCriteria(criteria);
			comment=mongoTemplate.findOne(query,CommentModel.class);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return comment;
		
	}

	
}
