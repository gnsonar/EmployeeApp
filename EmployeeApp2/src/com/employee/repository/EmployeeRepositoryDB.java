package com.employee.repository;

import com.employee.model.Employee;

public interface EmployeeRepositoryDB<T> {

	public long saveObject(T t);
	public T getObject(long id);
	public void updateObject(T emp);
}
