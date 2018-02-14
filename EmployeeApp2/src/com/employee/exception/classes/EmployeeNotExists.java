package com.employee.exception.classes;

public class EmployeeNotExists extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmployeeNotExists(String message){
		super(message);
	}

}
