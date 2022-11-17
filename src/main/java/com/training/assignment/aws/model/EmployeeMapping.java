package com.training.assignment.aws.model;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

@RedisHash

public class EmployeeMapping implements Serializable{
	
	
	private int Id;
	private String deptname;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public EmployeeMapping(int id, String deptname) {
		super();
		Id = id;
		this.deptname = deptname;
	}
	
	

}
