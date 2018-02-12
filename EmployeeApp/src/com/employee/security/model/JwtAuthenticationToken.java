package com.employee.security.model;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private String token;
	

	public JwtAuthenticationToken(String token) {
		super(null, null);
		this.token = token;
	}

	/**
	 * 
	 */
	public String getToken() {
		return token;
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

}
