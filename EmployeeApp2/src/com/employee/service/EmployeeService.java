package com.employee.service;


import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.employee.exception.classes.EmployeeAppException;
import com.employee.model.Employee;

public interface EmployeeService {
	
	public Long addEmployee(Employee emp) throws EmployeeAppException;
	
	public void addEmployeePhoto(long empId, byte[] photo);
	
	public Employee getEmployee(long empId);
	
	public boolean deleteEmployee(long empid) throws EmployeeAppException;
	
	public boolean updateEmployee(long empid, Employee emp) throws EmployeeAppException;

	public List<Employee> getAllEmployees(String user);

	public byte[] getEmployeePhoto(long empId) throws RuntimeException, SerialException;
}
