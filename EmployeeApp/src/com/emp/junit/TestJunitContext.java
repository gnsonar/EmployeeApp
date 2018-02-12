package com.emp.junit;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.employee.service.EmployeeService;

@Configuration
public class TestJunitContext {

	@Bean
	public EmployeeService getEmpSer(){
		return Mockito.mock(EmployeeService.class);
	}
}
