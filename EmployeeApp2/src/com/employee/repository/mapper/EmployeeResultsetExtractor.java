package com.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.employee.model.Employee;
import com.employee.util.constants.EmployeeAppConstants;

public class EmployeeResultsetExtractor implements ResultSetExtractor<Employee> , EmployeeAppConstants{

	@Override
	public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
		Employee emp = new Employee();
		emp.setId(rs.getLong(EMPLOYEE_COLUMN_ID));
		emp.setFirstName(rs.getString(EMPLOYEE_COLUMN_FNAME));
		emp.setLastName(rs.getString(EMPLOYEE_COLUMN_LNAME));
		emp.setEmail(rs.getString(EMPLOYEE_COLUMN_EMAIL));
		emp.setPhoto(rs.getBytes(EMPLOYEE_COLUMN_PHOTO));
		emp.setUser(rs.getString(EMPLOYEE_COLUMN_USER));
		return emp;
	}

}
