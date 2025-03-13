package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Comment")
public class CommentDesModel {
	
	@Id
	private String commentId;
	private Date date;
	private String repliedBy;
	private String repliedTo;
	private String comments;
	
	public CommentDesModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentDesModel(String commentId, Date date, String repliedBy, String repliedTo, String comments) {
		super();
		this.commentId = commentId;
		this.date = date;
		this.repliedBy = repliedBy;
		this.repliedTo = repliedTo;
		this.comments = comments;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate() {
		this.date = new Date();
	}
	public String getRepliedBy() {
		return repliedBy;
	}
	public void setRepliedBy(String repliedBy) {
		this.repliedBy = repliedBy;
	}
	public String getRepliedTo() {
		return repliedTo;
	}
	public void setRepliedTo(String repliedTo) {
		this.repliedTo = repliedTo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
