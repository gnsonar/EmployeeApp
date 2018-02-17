package com.employee.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.employee.model.AppUser;
import com.employee.security.handler.EmployeeAuthenticationSucessHandler;
import com.employee.security.model.AuthenticationToken;
import com.employee.security.model.JwtAuthenticationToken;
import com.employee.util.JWTParser;
import com.employee.util.constants.EmployeeAppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class EmployeeAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements EmployeeAppConstants{

	public EmployeeAuthenticationFilter(String requiresAuthenticationRequestMatcher) {
		
		super(requiresAuthenticationRequestMatcher);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(requiresAuthenticationRequestMatcher));
		setAuthenticationManager(new EmployeeAuthenticationManager());
		setAuthenticationSuccessHandler(new EmployeeAuthenticationSucessHandler());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		
		String token = req.getHeader(AUTH_HEADER_NAME);
		
		AuthenticationToken authToken = new AuthenticationToken(null, "");
		JwtAuthenticationToken token1 = new JwtAuthenticationToken(token);
		try{
			AppUser user = JWTParser.parseToken(token);
			authToken = new AuthenticationToken(user.getGrantedAuths(), user.getUsername());
			
		}catch(ExpiredJwtException e){
			authToken.setAuthenticated(false);
			res.sendError(JWT_TOKEN_EXPIRED_ERR_CODE, JWT_TOKEN_EXPIRED_ERR_CODE_DES);
		}catch (MalformedJwtException me) {
			authToken.setAuthenticated(false);
			res.sendError(JWT_TOKEN_MALFORMED_ERR_CODE, JWT_TOKEN_MALFORMED_ERR_CODE_DES);
		}catch (UnsupportedJwtException ue) {
			authToken.setAuthenticated(false);
			res.sendError(JWT_TOKEN_UNSUPPOTED_ERR_CODE, JWT_TOKEN_UNSUPPOTED_ERR_CODE_DES);
		}catch (Exception ue) {
			authToken.setAuthenticated(false);
			res.sendError(JWT_TOKEN_EXCEPTION_ERR_CODE, JWT_TOKEN_EXCEPTION_ERR_CODE_DES);
		}
		//User user = new User(claims.get("username").toString(),"", grantedAuths);
		//AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
		return getAuthenticationManager().authenticate(authToken);
	}
	
	/*@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(req, res,chain);
	}*/
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
}
