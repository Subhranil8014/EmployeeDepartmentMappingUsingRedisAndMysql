package com.training.assignment.aws.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.training.assignment.aws.daoimpl.EmployeeDaoImpl;
import com.training.assignment.aws.exception.EmployeeNotFoundException;
import com.training.assignment.aws.model.Employee;
import com.training.assignment.aws.model.EmployeeMapping;


@EnableWebMvc
@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;
	
    //saves data in employee table and mapping of id and dept name in redis
	//Api have validations on compulsory parameters value 
	@PostMapping
	public ResponseEntity< Employee> saveEmployee(@RequestBody @Valid Employee employee) {
		 employeeDaoImpl.saveEmployee(employee);
		 return new ResponseEntity<>( employee,HttpStatus.CREATED);

	}
	
	//to fetch all data from redis
	@GetMapping("/redis")
	public List<EmployeeMapping> getAllMappingDetails() {
		return employeeDaoImpl.getAllMappingDetails();
	}
	
	
	//get a single employee detail
	//id is checked in redis for deptname and id mapping
	//if dept is null then defaul_dept is assigned and updated in redis and mysql both
	//if id doesn't exist Api shows proper error message
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeDetail(@PathVariable int id) throws EmployeeNotFoundException {

		return ResponseEntity.ok(employeeDaoImpl.getEmployeeDetail(id));
	}
	
	//get only dept name by Id
	//id is checked in redis for deptname and id mapping
	//if dept is null then defaul_dept is assigned and updated in redis and mysql both
	//if id doesn't exist Api shows proper error message
	@GetMapping("/deptname/{id}")
	public ResponseEntity<String>getDeptNameById(@PathVariable int id) throws EmployeeNotFoundException{
		return ResponseEntity.ok(employeeDaoImpl.getDeptNameById(id));
	}
	
	
	//read all data in employee table
	@GetMapping
	public List<Employee> getEmployees(){
		return employeeDaoImpl.getEmployees();
	}
	
	
	//update any employee data(name and dept name) 
	//updates both employee table and redis
	//if id doesn't exist proper error message is shown
	@PutMapping
	public ResponseEntity< Employee> updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeNotFoundException {
		employeeDaoImpl.updateEmployee(employee);
		return new ResponseEntity<>( employee,HttpStatus.CREATED);
	}
	
	//delete any employee data by id 
	//deletes from both employee table and redis
	//if id doesn't exist proper error message is shown by API
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable int id) throws EmployeeNotFoundException {
		return employeeDaoImpl.deleteEmployee(id);			
	}



}
