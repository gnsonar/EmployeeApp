package com.employee.exception.classes;

import org.springframework.security.core.AuthenticationException;

public class JwtAthenticationException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	public JwtAthenticationException(String msg,String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
}
