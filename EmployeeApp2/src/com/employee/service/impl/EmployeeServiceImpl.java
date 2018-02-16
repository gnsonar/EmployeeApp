package com.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.employee.exception.classes.EmployeeAppException;
import com.employee.exception.classes.EmployeeNotExists;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepositoryDB;
import com.employee.service.EmployeeService;
import com.employee.util.EmployeeAppConstants;

@Service
public class EmployeeServiceImpl implements EmployeeService , EmployeeAppConstants{

	Map<Long, Employee> employeeList = new HashMap<>();
	
	@Autowired
	@Qualifier("hbTemplate")
	EmployeeRepositoryDB<Employee> employeeRepo;
	
	@Override
	public Long addEmployee(Employee emp) throws EmployeeAppException {
		Long id = 0l;
		try{
			id = employeeRepo.saveObject(emp);
			emp.setId(id);
			employeeList.put(id, emp);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EmployeeAppException(EMPLOYEE_APP_CREATE_EXCEPTION_MSG);
		}
		return id;
	}

	@Override
	public Employee getEmployee(long empId) {
		
		Employee emp = employeeList.get(empId);
		if(emp == null){
			throw new EmployeeNotExists(EMPLOYEE_NOTEXISTS_EXCEPTION_MSG);
		}
		return emp;
	}

	@Override
	public boolean deleteEmployee(long empid) throws EmployeeAppException {
		boolean sts = false;
		try{
			if(employeeList.remove(empid) != null){
				sts = true;
			}
		}catch(Exception e){
			throw new EmployeeAppException(EMPLOYEE_APP_DELETE_EXCEPTION_MSG);
		}
		
		return sts;
	}
	
	@Override
	public boolean updateEmployee(long empid, Employee emp) throws EmployeeAppException {

		boolean sts = false;
		try{
			if(employeeList.put(empid, emp) != null){
				sts = true;
			}
		}catch(Exception e){
			throw new EmployeeAppException(EMPLOYEE_APP_UPDATE_EXCEPTION_MSG);
		}
		
		return sts;
	}
	
	@Override
	public List<Employee> getAllEmployees(String user) {
		List<Employee> empList = null;
		if(EMPLOYEE_USER_ADMIN.equalsIgnoreCase(user)){
			empList = new ArrayList<>(employeeList.values());
		}else{
			empList = employeeList.values().stream().filter(emp -> emp.getUser().equalsIgnoreCase(user))
			.collect(Collectors.toList());
		}
		
		return empList;
	}

	@Override
	public void addEmployeePhoto(long empId, byte[] photo) {
		
		Employee emp = employeeRepo.getObject(empId);
		emp.setPhoto(photo);
		employeeRepo.updateObject(emp);
		
		/*Optional<Employee> empObj = employeeList.values().stream().filter(emp -> emp.getId() == empId).findFirst();
		
		if(empObj.get() != null){
			empObj.get().setPhoto(photo);
		}*/
	}

	@Override
	public byte[] getEmployeePhoto(long empId) {
		Optional<Employee> empObj = employeeList.values().stream().filter(emp -> emp.getId() == empId).findFirst();
		
		return employeeRepo.getObject(empId).getPhoto();
	}

	
}
