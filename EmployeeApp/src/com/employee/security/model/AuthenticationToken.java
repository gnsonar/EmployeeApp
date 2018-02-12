package com.employee.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationToken extends AbstractAuthenticationToken {

	Object principle = null;
	public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object priciple) {
		super(authorities);
		super.setAuthenticated(true);
		this.principle = priciple;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getPrincipal() {
		return principle;
	}

}
