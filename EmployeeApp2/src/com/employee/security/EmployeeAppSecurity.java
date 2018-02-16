package com.employee.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.employee.security.config.AuthenticationProviderJWT;
import com.employee.security.config.EmployeeAuthenticationFilter;
import com.employee.security.config.EmployeeAuthenticationManager;
import com.employee.security.handler.EmployeeAuthenticationFailure;
import com.employee.security.handler.EmployeeAuthenticationSucessHandler;

@Configuration
@EnableWebSecurity
public class EmployeeAppSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationProviderJWT authenticationProviderJWT;
	
	@Autowired
	private EmployeeAuthenticationSucessHandler employeeAuthenticationSucessHandler;
	
	@Autowired
	private EmployeeAuthenticationManager employeeAuthenticationManager;
	
	@Autowired
	private EmployeeAuthenticationFailure employeeAuthenticationFailure;
	
	
	public EmployeeAuthenticationFilter getFilter(){
		EmployeeAuthenticationFilter filter = new EmployeeAuthenticationFilter("/employees/**");
		//filter.setAuthenticationManager(new ProviderManager(Arrays.asList(authenticationProviderJWT)));
		filter.setAuthenticationManager(employeeAuthenticationManager);
		filter.setAuthenticationSuccessHandler(employeeAuthenticationSucessHandler);
		return filter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http.csrf().disable();
		
		http.exceptionHandling().authenticationEntryPoint(employeeAuthenticationFailure);
		
		http.authorizeRequests().antMatchers("/home",
											 "/index.html",
											 "/static/**","/validateToken","/").permitAll();
		
		http.authorizeRequests().antMatchers("/employees/**").authenticated();
		
		http.authorizeRequests().anyRequest().authenticated();
		
		//http.authorizeRequests().antMatchers("/employees/saveEmployee").access("hasAuthority('ROLE_ADMIN')");
	
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		http.addFilterBefore(getFilter(),UsernamePasswordAuthenticationFilter.class);
		
		http.headers().cacheControl();
		
	}
	
}
