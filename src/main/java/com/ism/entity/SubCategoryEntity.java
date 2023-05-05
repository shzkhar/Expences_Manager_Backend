package com.ism.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SubCategory")
public class SubCategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subsategoryid;
	private String subsategory;
	
	public Integer getSubsategoryid() {
		return subsategoryid;
	}
	public void setSubsategoryid(Integer subsategoryid) {
		this.subsategoryid = subsategoryid;
	}
	public String getSubsategory() {
		return subsategory;
	}
	public void setSubsategory(String subsategory) {
		this.subsategory = subsategory;
	}
	
	
}
