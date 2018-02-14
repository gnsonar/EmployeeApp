package com.employee.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("Auth success");
		if(authentication.isAuthenticated()){
			clearAuthenticationAttributes(request);
			
			String context = request.getContextPath();
			String fullURL = request.getRequestURI();
			String url = fullURL.substring(fullURL.indexOf(context)+context.length());

			request.getRequestDispatcher(url).forward(request, response);
		}
	}
}
