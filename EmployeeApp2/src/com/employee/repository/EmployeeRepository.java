package com.employee.repository;

import com.employee.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EmployeeRepository {

	public boolean addEmployee(Employee emp) throws JsonProcessingException;
}
