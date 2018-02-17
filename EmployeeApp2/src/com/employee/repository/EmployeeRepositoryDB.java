package com.employee.repository;

import java.util.List;

public interface EmployeeRepositoryDB<T> {

	public long saveObject(T t);
	public T getObject(long id);
	public void updateObject(T emp);
	public List<T> getAllObject();
	public List<T> getAllObject(String user);
	void deleteObject(T emp);
}
