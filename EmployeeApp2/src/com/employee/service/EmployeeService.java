package com.employee.service;


import java.util.List;

import com.employee.exception.classes.EmployeeAppException;
import com.employee.model.Employee;

public interface EmployeeService {
	
	public Integer addEmployee(Employee emp) throws EmployeeAppException;
	
	public void addEmployeePhoto(String empId, byte[] photo);
	
	public Employee getEmployee(Integer empId);
	
	public boolean deleteEmployee(Integer empid) throws EmployeeAppException;
	
	public boolean updateEmployee(Integer empid, Employee emp) throws EmployeeAppException;

	public List<Employee> getAllEmployees(String user);

	public byte[] getEmployeePhoto(String empId);
}
