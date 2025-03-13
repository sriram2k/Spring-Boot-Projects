package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Comment")
public class CommentModel {
	
	@Id
	private String defectId;
	private String defectStatus;
	private List<CommentDesModel> commentDescription;
	
	
	public CommentModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentModel(String defectId, String defectStatus, List<CommentDesModel> commentDescription) {
		super();
		this.defectId = defectId;
		this.defectStatus = defectStatus;
		this.commentDescription = commentDescription;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public List<CommentDesModel> getCommentDescription() {
		return commentDescription;
	}
	public void setCommentDescription(List<CommentDesModel> commentDescription) {
		this.commentDescription = commentDescription;
	}
	
}
	