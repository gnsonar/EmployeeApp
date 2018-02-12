package com.emp.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.employee.LoginController;
import com.employee.dto.LoginLogoutDTO;
import com.employee.model.AppUser;
import com.employee.service.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerJunit {

	@InjectMocks
	LoginController cntr = new LoginController();
	
	@Mock
	LoginService loginservice;
	
	@Mock
	HttpServletRequest request;
	
	@Test
	public void testAuthenticationMethod() throws UnsupportedEncodingException{
		
		LoginLogoutDTO dto = new LoginLogoutDTO();
		dto.setUsername("admin");
		dto.setPassword("admin");
		
		AppUser user = new AppUser();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setRole("admin");
		
		
		when(loginservice.authenticateUser("admin", "admin")).thenReturn(true);
		when(loginservice.getUserDetails("admin")).thenReturn(user);
		
		dto = cntr.login(dto, request);
		assertEquals(dto.getSts(), "P");
		
		assertNotNull(dto.getToken());
		
		
		when(loginservice.authenticateUser("admin", "admin")).thenReturn(false);
		when(loginservice.getUserDetails("admin")).thenReturn(user);
		dto = cntr.login(dto, request);
		assertEquals(dto.getSts(), "F");
	}
}
