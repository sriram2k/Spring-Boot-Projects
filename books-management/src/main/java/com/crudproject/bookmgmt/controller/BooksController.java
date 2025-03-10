package com.crudproject.bookmgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crudproject.bookmgmt.entity.Book;
import com.crudproject.bookmgmt.service.impl.BookServiceImpl;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;

@Api(value = "Books Controller")
@RestController
@RequestMapping("/book")
public class BooksController {

	@Autowired
	BookServiceImpl bookserviceImpl;

	@GetMapping("/all")
	public ResponseEntity<List<Book>> getAllBooks() {

		List<Book> bookList = bookserviceImpl.getAllBooks();

		ResponseEntity<List<Book>> response = null;

		if (!bookList.isEmpty()) {
			response = new ResponseEntity<>(bookList, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(bookList, HttpStatus.NOT_FOUND);
		}

		System.out.println("All book fetched " + bookList);

		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

		Book bookDetails = bookserviceImpl.getBookById(id);

		ResponseEntity<Book> response = null;

		if (bookDetails != null) {
			response = new ResponseEntity<>(bookDetails, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(bookDetails, HttpStatus.NOT_FOUND);
		}

		return response;
	}

	
	@GetMapping("/name/{name}")
	public ResponseEntity<Book> getBookByName(@PathVariable String name) {

		ResponseEntity<Book> response = null;

		Book bookDetails = bookserviceImpl.getBookByName(name);

		if (bookDetails != null) {
			response = new ResponseEntity<>(bookDetails, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(bookDetails, HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
	@GetMapping("/author/{author}")
	public ResponseEntity<Book> getBookByAuthor(@PathVariable String author) {

		ResponseEntity<Book> response = null;

		Book bookDetails = bookserviceImpl.getBookByAuthor(author);

		if (bookDetails != null) {
			response = new ResponseEntity<>(bookDetails, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(bookDetails, HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
	@PostMapping("/add")
	public String addBook(@Valid @RequestBody Book book) {

		bookserviceImpl.addBook(book);

		String response = "Book added successfully";
		System.out.println(response);

		return response;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public String updateBook(@PathVariable Integer id, @Valid @RequestBody Book book) {

		Book updatedBookDetails = bookserviceImpl.updateBook(id, book);

		String response = "Book updated successfully";
		System.out.println(response + " " + updatedBookDetails);

		return response;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable Integer id) {

		bookserviceImpl.deleteBook(id);

		String response = "Book " + id + " deleted successfully";
		System.out.println(response);

		return response;
	}

	@ControllerAdvice
	public class GlobalExceptionHandler {

		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
			BindingResult bindingResult = ex.getBindingResult();
			Map<String, String> errors = new HashMap<>();

			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
	}

}
