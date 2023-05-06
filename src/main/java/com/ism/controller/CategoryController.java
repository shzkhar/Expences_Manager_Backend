package com.ism.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ism.bean.CustomResponse;
import com.ism.entity.CategoryEntity;
import com.ism.repository.CategoryRepository;
@CrossOrigin
@RestController
public class CategoryController {
	@Autowired
	CategoryRepository catagoryRepository;
	
    @PostMapping("/addCategory")
	public ResponseEntity<CustomResponse<CategoryEntity>> addCategoryy(@RequestBody CategoryEntity categoryEntity)
	{
    	CategoryEntity categoryName = catagoryRepository.findByCategoryname(categoryEntity.getCategoryname());
    	
    	CustomResponse<CategoryEntity> resp = new CustomResponse<>();
    	
    	if(categoryName == null)
    	{
    		catagoryRepository.save(categoryEntity);
    		
    		resp.setData(categoryEntity);
    		resp.setMsg("Category Addes Successfully");
    		return ResponseEntity.ok(resp);
    	}
    	else
    	{	
    		resp.setMsg("Category Already Exist");
    		return ResponseEntity.unprocessableEntity().body(resp);
    	}
	}
    
    @GetMapping("/getAllCategory")
	public ResponseEntity<CustomResponse<List<CategoryEntity>>> getAll() {

		List<CategoryEntity> category = catagoryRepository.findAll();
		CustomResponse<List<CategoryEntity>> resp = new CustomResponse<>();
		resp.setData(category);
		resp.setMsg("All Category Feched");

		return ResponseEntity.ok(resp);
	}
    
    @DeleteMapping("/deleteCategoryById/{categoryid}")
    public ResponseEntity<CustomResponse<CategoryEntity>> deleteCategoryByIdd(@PathVariable("categoryid") Integer categoryid)
    {
    	CategoryEntity categoryEntity = catagoryRepository.findByCategoryid(categoryid);
    	catagoryRepository.deleteById(categoryid);
    	
    	CustomResponse<CategoryEntity> resp = new CustomResponse<>();
    	
    	resp.setData(categoryEntity);
    	resp.setMsg("Category Deleted Successfully");
    	return ResponseEntity.ok(resp);
    	
    }
}
