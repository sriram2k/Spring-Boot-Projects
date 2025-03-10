package com.crudproject.bookmgmt.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudproject.bookmgmt.entity.Book;
import com.crudproject.bookmgmt.repo.BookRepo;
import com.crudproject.bookmgmt.service.BookService;


@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepo bookRepo;
	
	/**
	 * Get all book details
	 */
	@Override
	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}
	
	/**
	 * Get book by ID
	 * 
	 * @param id
	 * @return book details
	 */
	@Override
	public Book getBookById(Integer id) {
		
		Optional<Book> bookDetails = bookRepo.findById(id);
		
		return (bookDetails.isPresent())?(bookDetails.get()):(null);
	}
	
	/**
	 * Get book by Name
	 * 
	 * @param name
	 * @return book details
	 */
	@Override
	public Book getBookByName(String name) {
		
		Book result = bookRepo.findByName(name);
		return result;
	}
	
	/**
	 * Get book by author
	 * 
	 * @param author
	 * @return book details
	 */
	@Override
	public Book getBookByAuthor(String author) {
		Book result = bookRepo.findByAuthor(author);
		return result;
	}
	
	/**
	 * Add new book
	 * 
	 * @param book
	 * @return null
	 */
	@Override
	public void addBook(Book book) {
		bookRepo.save(book);
	}
	
	/**
	 * Update book details
	 * 
	 * @param id
	 * @param book
	 * @return Updated book details
	 */
	@Override
	public Book updateBook(Integer id, Book book) {
		
		Book existingBookDetails = bookRepo.findById(id).get();
		existingBookDetails.setAuthor(book.getAuthor());
		existingBookDetails.setName(book.getName());
		
		bookRepo.save(existingBookDetails);
		
		return existingBookDetails;
	}
	
	/**
	 * Delete book by ID
	 * 
	 * @param id
	 * @return null
	 */
	@Override
	public void deleteBook(Integer id) {
		bookRepo.deleteById(id);
	}
	
}
