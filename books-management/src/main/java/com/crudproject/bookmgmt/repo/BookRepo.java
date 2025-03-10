package com.crudproject.bookmgmt.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crudproject.bookmgmt.entity.Book;

@Repository
public interface BookRepo extends MongoRepository<Book, Integer>{
	
	public Book findByName(String name);
	public Book findByAuthor(String author);
}
