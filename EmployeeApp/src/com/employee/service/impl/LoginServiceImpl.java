package com.employee.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.model.AppUser;
import com.employee.service.LoginService;
import com.employee.util.EmployeeAppConstants;

@Service
public class LoginServiceImpl implements LoginService , EmployeeAppConstants{

	List<AppUser> userList = new ArrayList<>(Arrays.asList(new AppUser("admin", "admin", "ROLE_ADMIN"),new AppUser("user", "user", "ROLE_USER")));
	
	AppUser currentUser = null;
	
	@Override
	public boolean authenticateUser(String username, String password) {
		
		Iterator<AppUser> itr = userList.iterator();
		
		while(itr.hasNext()){
			AppUser user = itr.next();
			if(username.equalsIgnoreCase(user.getUsername()) && password.equalsIgnoreCase(user.getPassword())){
				currentUser = user;
				return true;
			}
		}
		return false;
	}

	@Override
	public AppUser getUserDetails(String username) {
		return currentUser;
	}
}
