package com.employee.security.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.employee.exception.classes.JwtAthenticationException;
import com.employee.model.AppUser;
import com.employee.security.model.AuthenticatedUser;
import com.employee.security.model.JwtAuthenticationToken;
import com.employee.util.JWTParser;
import com.employee.util.constants.EmployeeAppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class AuthenticationProviderJWT extends AbstractUserDetailsAuthenticationProvider{

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
			
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken auth) throws AuthenticationException {
		
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) auth;
		AppUser user = null;
		try {
			user = JWTParser.parseToken(authentication.getToken());
		} catch (Exception e) {
			throw new JwtAthenticationException("","");
			
		} 
		return new AuthenticatedUser(user.getUsername(), authentication.getToken(), user.getGrantedAuths());
	}

}
