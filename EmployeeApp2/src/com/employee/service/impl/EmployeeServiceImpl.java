package com.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.exception.classes.EmployeeAppException;
import com.employee.exception.classes.EmployeeNotExists;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.employee.util.EmployeeAppConstants;

@Service
public class EmployeeServiceImpl implements EmployeeService , EmployeeAppConstants{

	Map<Integer, Employee> employeeList = new HashMap<>();
	
	
	
	@Override
	public Integer addEmployee(Employee emp) throws EmployeeAppException {
		Integer id = 0;
		try{
			id = Math.abs(new Random().nextInt());
			emp.setId(String.valueOf(id));
			
			employeeList.put(id, emp);
			
			//empRepo.addEmployee(emp);
		}catch(Exception e){
			throw new EmployeeAppException(EMPLOYEE_APP_CREATE_EXCEPTION_MSG);
		}
		return id;
	}

	@Override
	public Employee getEmployee(Integer empId) {
		
		Employee emp = employeeList.get(empId);
		if(emp == null){
			throw new EmployeeNotExists(EMPLOYEE_NOTEXISTS_EXCEPTION_MSG);
		}
		return emp;
	}

	@Override
	public boolean deleteEmployee(Integer empid) throws EmployeeAppException {
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
	public boolean updateEmployee(Integer empid, Employee emp) throws EmployeeAppException {

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
	public void addEmployeePhoto(String empId, byte[] photo) {
		Optional<Employee> empObj = employeeList.values().stream().filter(emp -> emp.getId().equalsIgnoreCase(empId)).findFirst();
		
		if(empObj.get() != null){
			empObj.get().setPhoto(photo);
		}
	}

	@Override
	public byte[] getEmployeePhoto(String empId) {
		Optional<Employee> empObj = employeeList.values().stream().filter(emp -> emp.getId().equalsIgnoreCase(empId)).findFirst();
		
		return empObj.get().getPhoto();
	}

	
}
