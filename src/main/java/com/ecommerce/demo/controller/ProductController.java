package com.ecommerce.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.common.ApiRespo;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.CategoryRepo;
import com.ecommerce.demo.repository.ProductRepo;
import com.ecommerce.demo.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Operation(summary="This is to Add Product in Db")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
			description="Sucessfully Added a category to Db",
			content= {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode="400",
			description="Ho Gaya Document",
			content= {@Content(mediaType = "application/json")}),
	})
	@PostMapping
	public ResponseEntity<ApiRespo> createProduct(@RequestBody ProductDto productDto){
		Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategory());
		if(!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiRespo(false, "category does not exists."),HttpStatus.BAD_REQUEST);
		}
		productService.createProduct(productDto, optionalCategory.get());
		return new ResponseEntity<>(new ApiRespo(true, "product has been added"),HttpStatus.CREATED);
	}
	
	@Operation(summary="This is to Get all products from Db.")
	@GetMapping
	public ResponseEntity<List<ProductDto>> getProducts(){
		List<ProductDto> products = productService.getAllProducts();
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ApiRespo> updateProduct(
			@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception{
		Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategory());
		if(!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiRespo(false, "category does not exists."),HttpStatus.BAD_REQUEST);
		}
		productService.updateProduct(productDto, productId);
		
		return new ResponseEntity<>(new ApiRespo(true, "Product has been Added"),HttpStatus.CREATED);
	}
}


