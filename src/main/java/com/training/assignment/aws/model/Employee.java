package com.training.assignment.aws.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class Employee implements Serializable{
	
	@Id
	@NotNull(message = "Id shouldn't be null")
	private int id;
	@NotBlank(message = "Name shouldn't be null")
	private String name;
	private String deptname;
	
	public Employee() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	public Employee(int id, String name, String deptname) {
		super();
		this.id = id;
		this.name = name;
		this.deptname = deptname;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", deptname=" + deptname + "]";
	}

}
