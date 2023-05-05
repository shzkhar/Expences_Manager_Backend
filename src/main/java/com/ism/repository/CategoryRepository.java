package com.ism.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ism.entity.CategoryEntity;


@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>{

	CategoryEntity findByCategoryname(String categoryname);
	List<CategoryEntity> findAll();
	
	CategoryEntity findByCategoryid(Integer categoryid);

}
