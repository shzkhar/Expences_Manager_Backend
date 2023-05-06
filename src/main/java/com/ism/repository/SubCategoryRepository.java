package com.ism.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ism.entity.CategoryEntity;
import com.ism.entity.SubCategoryEntity;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategoryEntity, Integer>{

	SubCategoryEntity findBySubcategoryname(String subcategoryname);

	List<SubCategoryEntity> findAll();

	SubCategoryEntity findBySubcategoryid(Integer subcategoryid);

	

	

}
