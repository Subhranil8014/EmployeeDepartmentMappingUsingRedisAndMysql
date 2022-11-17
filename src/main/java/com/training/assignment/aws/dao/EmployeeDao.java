package com.training.assignment.aws.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.assignment.aws.model.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer>{
	
	//public Employee findByName(String name);
	public Employee findById(int id);

}
