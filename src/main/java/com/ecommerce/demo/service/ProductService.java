package com.ecommerce.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.CategoryRepo;
import com.ecommerce.demo.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setCategory(category);
		product.setPrice(productDto.getPrice());
		productRepo.save(product);
	}
	
	public List<ProductDto> getAllProducts() {
		List<Product> allProducts = productRepo.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		for(Product product : allProducts) {
			productDtos.add(getProductDto(product));
		}
		return productDtos;
	}
	
	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setDescription(product.getDescription());
		productDto.setImageURL(product.getImageURL());
		productDto.setName(product.getName());
		productDto.setCategory(product.getCategory().getId());
		productDto.setPrice(product.getPrice());
		return productDto;
	}

	public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
			Optional<Product> optionalProduct = productRepo.findById(productId);
			if(!optionalProduct.isPresent()) {
				throw new Exception("product not present");
			}
			
			Optional<Category> optionalCategory = categoryRepo.findById(productDto.getId());
			if(!optionalCategory.isPresent()) {
				throw new Exception("category not present in database");
			}
			Product product = optionalProduct.get();
			Category category = optionalCategory.get();
			product.setDescription(productDto.getDescription());
			product.setImageURL(productDto.getImageURL());
			product.setName(productDto.getName());
			product.setCategory(categoryRepo.findById(productDto.getId()).get());
			product.setPrice(productDto.getPrice());
			productRepo.save(product);
	}
}
