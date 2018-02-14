package com.employee.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.employee.exception.classes.JwtAthenticationException;

@Component
public class EmployeeAuthenticationManager implements AuthenticationManager{

	@Override
	public Authentication authenticate(Authentication auth) throws JwtAthenticationException {
		
		return auth;
	}

}
