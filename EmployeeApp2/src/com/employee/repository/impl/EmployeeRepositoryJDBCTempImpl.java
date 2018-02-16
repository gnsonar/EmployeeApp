package com.employee.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepositoryDB;

@Repository
@Qualifier("jdbcTemplate")
public class EmployeeRepositoryJDBCTempImpl implements EmployeeRepositoryDB<Employee>{

	@Autowired
	JdbcTemplate jdbcTemplate;

		@Override
	public long saveObject(Employee t) {
		return 0;
	}

	@Override
	public Employee getObject(long id) {
		return null;
	}

	@Override
	public void updateObject(Employee emp) {
		// TODO Auto-generated method stub
		
	}
}
