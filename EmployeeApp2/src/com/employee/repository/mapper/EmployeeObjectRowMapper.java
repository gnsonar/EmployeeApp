package com.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.employee.model.Employee;

public class EmployeeObjectRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int index) throws SQLException {
		EmployeeResultsetExtractor extractor = new EmployeeResultsetExtractor();
		return extractor.extractData(rs);
	}

}
