package com.employee.util.constants;

public interface EmployeeAppConstants {

	/*
	 * General Employee Application Constants
	 */
	String BLANK_STR = "";
	String EMP_APP_SER_SUCESS = "success";
	String EMP_APP_SER_FAIL = "fail";
	String EMP_APP_ID_0 = "0";
	
	String EMP_LOGIN_PASS_CODE = "P";
	String EMP_LOGIN_FAIL_CODE = "F";
	
	/*
	 * Constants for Login Token Generation
	 */
	String AUTH_HEADER_NAME = "Authorization";
	String JWT_TOKEN_USERNAME_CLAIM = "username";
	String JWT_TOKEN_ROLE_CLAIM = "role";
	int JWT_TOKEN_EXPIRATION_PERIOD = 1;
	
	String JWT_TOKEN_SECRET_KEY = "emp-app-secret-key";
	String JWT_TOKEN_ENCODING_TYPE = "UTF-8";
	
	int JWT_TOKEN_EXPIRED_ERR_CODE = 601;
	String JWT_TOKEN_EXPIRED_ERR_CODE_DES = "Token Expired";
	
	int JWT_TOKEN_MALFORMED_ERR_CODE = 602;
	String JWT_TOKEN_MALFORMED_ERR_CODE_DES = "Invalid Token";
	
	int JWT_TOKEN_UNSUPPOTED_ERR_CODE = 603;
	String JWT_TOKEN_UNSUPPOTED_ERR_CODE_DES = "Unsupported Token";
	
	int JWT_TOKEN_EXCEPTION_ERR_CODE = 604;
	String JWT_TOKEN_EXCEPTION_ERR_CODE_DES = "Error in Token";
	
	
	/*
	 * Constants for Exception Handler
	 */
	String EMPLOYEE_APP_CREATE_EXCEPTION_MSG = "Exception in Employee Creation";
	String EMPLOYEE_NOTEXISTS_EXCEPTION_MSG = "Employee Not exists in records";
	String EMPLOYEE_APP_DELETE_EXCEPTION_MSG = "Exception in Employee Deletion";
	String EMPLOYEE_APP_UPDATE_EXCEPTION_MSG = "Exception in Employee Update";
	
	String EMPLOYEE_EXP_HANDLER_ERR_CODE = "501";
	String EMPLOYEE_EXP_HANDLER_EMPNOTEXIST_CODE = "201";
	String EMPLOYEE_EXP_HANDLER_EXP_MSG = "Something went Wrong in application";
	String EMPLOYEE_EXP_HANDLER_EMPAPP_EXP_MSG = "Maintaince is going on Please try after sometime";
	String EMPLOYEE_EXP_HANDLER_RTEXP_MSG = "Internal Server Error";
	
	String EMPLOYEE_USER_ADMIN = "admin";
	
	/**
	 * EEMPLOYEE table column name
	 */
	String EMPLOYEE_COLUMN_ID = "ID";
	String EMPLOYEE_COLUMN_FNAME = "FIRSTNAME";
	String EMPLOYEE_COLUMN_LNAME = "LASTNAME";
	String EMPLOYEE_COLUMN_EMAIL = "EMAIL";
	String EMPLOYEE_COLUMN_PHOTO = "PHOTO";
	String EMPLOYEE_COLUMN_USER = "USERCREATED";
}
