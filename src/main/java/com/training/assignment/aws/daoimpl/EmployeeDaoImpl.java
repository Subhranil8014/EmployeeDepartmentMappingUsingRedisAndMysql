package com.training.assignment.aws.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.training.assignment.aws.dao.EmployeeDao;
import com.training.assignment.aws.exception.EmployeeNotFoundException;
import com.training.assignment.aws.model.Employee;
import com.training.assignment.aws.model.EmployeeMapping;


@Service
public class EmployeeDaoImpl {


	@Autowired
	private EmployeeDao employeedao;


	private HashOperations hashOperations;
	public static final String HASH_KEY ="EmployeeMapping";
	

	public EmployeeDaoImpl(RedisTemplate redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}
	

    //saves data in employee db and redis
   
	public void saveEmployee(Employee employee) {

		employeedao.save(employee);
		EmployeeMapping employeeMapping=new EmployeeMapping(employee.getId(),employee.getDeptname());
		hashOperations.put(HASH_KEY, employee.getId(), employeeMapping);
		//return employee;

	}

   
	//to fetch dept_name using employeeId If any id mapping not exists in redis then assigns name as ( default_dept )
    
	public Employee getEmployeeDetail(int id) throws EmployeeNotFoundException {
		if(hashOperations.get(HASH_KEY, id)==null)
			throw new EmployeeNotFoundException("User not found with id: "+id);
		if( (((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname()==null)||((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname().isEmpty()) {
			//System.out.println(employeedao.findById(id)+"hello");
			EmployeeMapping employeeMapping=new EmployeeMapping(id,"default_dept");
			hashOperations.put(HASH_KEY, id, employeeMapping);
			Employee employee=employeedao.findById(id);
			employee.setDeptname("default_dept");
			employeedao.save(employee);
			
			return employeedao.findById(id);
			
		}
		else
			return employeedao.findById(id);
			
	}
	
	public String getDeptNameById(int id) throws EmployeeNotFoundException {
		if(hashOperations.get(HASH_KEY, id)==null)
			throw new EmployeeNotFoundException("User not found with id: "+id);
		if( (((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname()==null)||((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname().isEmpty()) {
			EmployeeMapping employeeMapping=new EmployeeMapping(id,"default_dept");
			hashOperations.put(HASH_KEY, id, employeeMapping);
			Employee employee=employeedao.findById(id);
			employee.setDeptname("default_dept");
			employeedao.save(employee);
			return ((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname();
		}
		else
			return ((EmployeeMapping) hashOperations.get(HASH_KEY, id)).getDeptname();
		
	}
    
    //to get all values in redis
    
	public List<EmployeeMapping> getAllMappingDetails() {

		return hashOperations.values(HASH_KEY);
	}
   
	
	//to get all values in employee table
	public List<Employee> getEmployees(){
		return employeedao.findAll();
	}
    
   
	
	//updates both employee db and redis
	public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
		if(hashOperations.get(HASH_KEY, employee.getId())==null)
			throw new EmployeeNotFoundException("This employee with id: "+employee.getId()+" doesn't exist.Can't be updated."+"Save employee first to update.");

		EmployeeMapping employeeMapping=new EmployeeMapping(employee.getId(),employee.getDeptname());
		hashOperations.put(HASH_KEY, employeeMapping.getId(), employeeMapping);	
		employeedao.save(employee);
	}
	
   
	//delete from both redis and employeedb using id
	public String deleteEmployee(int id) throws EmployeeNotFoundException {
		if(hashOperations.get(HASH_KEY, id)==null)
			throw new EmployeeNotFoundException("This employee with id: "+id+" doesn't exist.Can't be deleted.");
		hashOperations.delete(HASH_KEY, id);
		employeedao.deleteById(id);
		return "Employee "+id+" has been deleted";
	}

	

}
