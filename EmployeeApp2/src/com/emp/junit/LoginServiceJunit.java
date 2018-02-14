package com.emp.junit;

import org.junit.Assert;
import org.junit.Test;

import com.employee.service.LoginService;
import com.employee.service.impl.LoginServiceImpl;

public class LoginServiceJunit {

	LoginService loginService = new LoginServiceImpl();
	
	@Test
	public void testLoginadmin(){
		
		boolean result = loginService.authenticateUser("admin", "admin");
		Assert.assertEquals(result, true);
		
		boolean result1 = loginService.authenticateUser("user", "user");
		Assert.assertEquals(result1, true);
		
		boolean result2 = loginService.authenticateUser("admin", "admin1");
		Assert.assertEquals(result2, false);
		
	}
}
