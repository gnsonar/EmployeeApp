package com.employee.exception;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.exception.classes.EmployeeAppException;
import com.employee.exception.classes.EmployeeNotExists;
import com.employee.exception.classes.JwtAthenticationException;
import com.employee.exception.model.ErrorResponse;
import com.employee.security.model.JwtAuthenticationToken;
import com.employee.util.EmployeeAppConstants;

@ControllerAdvice
public class EmployeeExceptionHandler implements EmployeeAppConstants{

	Optional<Throwable> rootCause = null;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> commonException(Exception ex){
		
		ex.printStackTrace();
		rootCause = Stream.iterate(ex, Throwable::getCause).filter(element -> element.getCause() == null).findFirst();

		ErrorResponse error = new ErrorResponse(EMPLOYEE_EXP_HANDLER_ERR_CODE, EMPLOYEE_EXP_HANDLER_EXP_MSG, rootCause.get().toString());
		
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(EmployeeAppException.class)
	public ResponseEntity<ErrorResponse> employeeAppExceptionException(EmployeeAppException ex){
		ex.printStackTrace();
		rootCause = Stream.iterate(ex, Throwable::getCause).filter(element -> element.getCause() == null).findFirst();
		
		ErrorResponse error = new ErrorResponse(EMPLOYEE_EXP_HANDLER_ERR_CODE, EMPLOYEE_EXP_HANDLER_EMPAPP_EXP_MSG, rootCause.get().toString());
		
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(EmployeeNotExists.class)
	public ResponseEntity<ErrorResponse> employeeNotExistsException(EmployeeNotExists ex){
		
		ErrorResponse error = new ErrorResponse(EMPLOYEE_EXP_HANDLER_EMPNOTEXIST_CODE, EMPLOYEE_NOTEXISTS_EXCEPTION_MSG, BLANK_STR);
		
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> processRuntimeException(Exception ex){
		
		System.out.println(ex);
		rootCause = Stream.iterate(ex, Throwable::getCause).filter(element -> element.getCause() == null).findFirst();
		ErrorResponse error = new ErrorResponse(EMPLOYEE_EXP_HANDLER_ERR_CODE, EMPLOYEE_EXP_HANDLER_RTEXP_MSG, rootCause.get().toString());
		
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
