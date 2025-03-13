package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Document(collection="Generator")
@JsonInclude(Include.NON_NULL)
public class Sequence {
	
	@Id
	public ObjectId _id;
	private String name;
	private int counter;
	private int incCounter;

	public String get_id() {
		return _id.toHexString();
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getIncCounter() {
		return incCounter;
	}
	public void setIncCounter(int incCounter) {
		this.incCounter = incCounter;
	}
	public Sequence(ObjectId _id, String name, int counter, int incCounter) {
		super();
		this._id = _id;
		this.name = name;
		this.counter = counter;
		this.incCounter = incCounter;
	}
	

}