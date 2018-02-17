package com.employee.repository.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepositoryDB;
import com.employee.repository.mapper.EmployeeObjectRowMapper;
import com.employee.util.constants.EmployeeJDBCTemplateSQLConstant;

@Repository
@Qualifier("jdbcTemplate")
public class EmployeeRepositoryJDBCTempImpl implements EmployeeRepositoryDB<Employee> , EmployeeJDBCTemplateSQLConstant{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public long saveObject(Employee t) {
		int id = Math.abs(new Random().nextInt());
		
		int result = jdbcTemplate.update(INSERT_EMP_DETAILS, new Object[] {
			id, t.getEmail(), t.getFirstName() , t.getLastName(), t.getUser()
		});
		return id;
	}

	@Override
	public Employee getObject(long id) {
		Employee emp = jdbcTemplate.queryForObject(SELECT_EMP_BY_ID, new Object[] {id} , new EmployeeObjectRowMapper());
		return emp;
	}

	@Override
	public void updateObject(Employee t) {
		
		jdbcTemplate.update(UPDATE_EMP_DETAILS, new Object[] {
			t.getEmail(), t.getFirstName() , t.getLastName(), t.getUser() , t.getPhoto(), t.getId()
		});
	}

	@Override
	public List<Employee> getAllObject() {
		return jdbcTemplate.query(SELECT_ALL_EMP, new EmployeeObjectRowMapper());
	}

	@Override
	public List<Employee> getAllObject(String user) {
		return jdbcTemplate.query(SELECT_EMP_BY_USER, new Object[] {user} , new EmployeeObjectRowMapper());
	}

	@Override
	public void deleteObject(Employee emp) {
		jdbcTemplate.update(DELETE_EMP_BY_ID, emp.getId());
	}
}