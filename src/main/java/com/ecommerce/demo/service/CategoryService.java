package com.ecommerce.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.repository.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public void createCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public List<Category> getCategories(){
		return categoryRepo.findAll();
	}
	
	public Category updateCategory(int categoryId, Category updateCategory) throws Exception {
		Category category = categoryRepo.findById(categoryId).get();
		category.setCategoryName(updateCategory.getCategoryName());
		category.setDescription(updateCategory.getDescription());
		category.setImageUrl(updateCategory.getDescription());
		categoryRepo.save(category);
		return category;
	}
	
	public Boolean exists(int id) {
		return categoryRepo.existsById(id);
	}
	
	public Category getCategory(int id) {
		return categoryRepo.findById(id).get();
	}
}
