package com.employee.exception;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.dto.ValidationErrorDTO;
import com.employee.dto.ValidationErrors;
import com.employee.exception.classes.EmployeeAppException;
import com.employee.exception.classes.EmployeeNotExists;
import com.employee.exception.classes.JwtAthenticationException;
import com.employee.exception.model.ErrorResponse;
import com.employee.security.model.JwtAuthenticationToken;
import com.employee.util.constants.EmployeeAppConstants;

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
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrors> validationErrorRespose(MethodArgumentNotValidException ex){
		ValidationErrors dto = new ValidationErrors();
		
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldError = result.getFieldErrors();
		
		fieldError.forEach(e -> {
			ValidationErrorDTO errDto = new ValidationErrorDTO();
			errDto.setField(e.getField());
			errDto.setMessage(e.getDefaultMessage());
			dto.setErrorList(errDto);
		});
		
		return new ResponseEntity<ValidationErrors>(dto, HttpStatus.BAD_REQUEST);
	}
}
