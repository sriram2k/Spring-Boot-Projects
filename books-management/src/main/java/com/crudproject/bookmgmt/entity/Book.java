package com.crudproject.bookmgmt.entity;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "book_details")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
	@Id
	@Unique
	private Integer id;
	
	@NotNull(message = "Name is required")
	@NotEmpty(message = "Name is required")
	private String name;
	
	@NotNull(message = "Author is required")
	@NotEmpty(message = "Author is required")
	private String author;
	
}
