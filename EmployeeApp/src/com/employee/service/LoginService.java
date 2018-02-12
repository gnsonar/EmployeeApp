package com.employee.service;

import java.io.UnsupportedEncodingException;

import com.employee.model.AppUser;

public interface LoginService {
	
	public boolean authenticateUser(String username, String password);

	public AppUser getUserDetails(String username);
}
