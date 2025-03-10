package com.crudproject.bookmgmt.service;

import java.util.List;

import com.crudproject.bookmgmt.entity.Book;

public interface BookService {
	
	public List<Book> getAllBooks();
	
	public Book getBookById(Integer id);
	
	public Book getBookByName(String name);
	
	public Book getBookByAuthor(String author);
	
	public void addBook(Book book);
	
	public Book updateBook( Integer id, Book book);
	
	public void deleteBook( Integer id);
	
}
