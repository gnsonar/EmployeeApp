package com.employee;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.LoginLogoutDTO;
import com.employee.service.LoginService;
import com.employee.util.EmployeeAppConstants;
import com.employee.util.JWTParser;

@RestController
public class LoginController implements EmployeeAppConstants{

	@Autowired
	LoginService loginService;
	
	@PostMapping(value = "/home" , produces = MediaType.APPLICATION_JSON_VALUE)
	public LoginLogoutDTO login(@RequestBody LoginLogoutDTO dto, HttpServletRequest request) throws UnsupportedEncodingException{
		
		if(loginService.authenticateUser(dto.getUsername(), dto.getPassword())){
			dto.setSts(EMP_LOGIN_PASS_CODE);
			dto.setIp(request.getRemoteHost());
			dto.setToken(JWTParser.generateToken(loginService.getUserDetails(dto.getUsername())));
		}else{
			dto.setSts(EMP_LOGIN_FAIL_CODE);
		}
		dto.setPassword(BLANK_STR);
		return dto;
	}
	
	@GetMapping(value = "/validateToken" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String validateToken(@RequestHeader("Authorization") String token){
		
		try{
			JWTParser.parseToken(token);
		}catch (Exception e) {
			return EMP_APP_SER_FAIL;
		}
		return EMP_APP_SER_SUCESS;
	}
}
