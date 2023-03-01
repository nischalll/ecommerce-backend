package com.ecommerce.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.ecommerce.demo.common.ApiRespo;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@Operation(summary="This is to Add category in Db")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
			description="Sucessfully Added a category to Db",
			content= {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode="404",
			description="Not Found",
			content= {@Content(mediaType = "application/json")}),
	})
	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		try {
			categoryService.createCategory(category);
			return ResponseEntity.of(Optional.of(category));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Operation(summary="This is to fetch All the Categories stored in Db")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
			description="Fetched All the categories from Db",
			content= {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode="404",
			description="Not Found",
			content= {@Content(mediaType = "application/json")}),
	})
	@GetMapping
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}
	
	@Operation(summary="This is to Get category by id from Db")
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") int id) {
		if(!categoryService.exists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Category category = categoryService.getCategory(id);
		return ResponseEntity.of(Optional.of(category));
	}
	
	@Operation(summary="Update the Category! ")
	@PutMapping("/{id}")
	public ResponseEntity<Category> putCategory(@PathVariable("id") int id, @RequestBody Category category){
		if(!categoryService.exists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		try {
			categoryService.updateCategory(id, category);
			return ResponseEntity.of(Optional.of(category));
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
