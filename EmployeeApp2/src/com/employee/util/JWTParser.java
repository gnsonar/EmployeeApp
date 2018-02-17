package com.employee.util;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.employee.model.AppUser;
import com.employee.util.constants.EmployeeAppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTParser implements EmployeeAppConstants{

	@SuppressWarnings("deprecation")
	public static String generateToken(AppUser user) throws UnsupportedEncodingException{
		Date date = new Date();
		date.setMinutes(date.getMinutes() + JWT_TOKEN_EXPIRATION_PERIOD);
		
		return Jwts.builder().
				setExpiration(date).
				setSubject(user.getUsername()).
				claim(JWT_TOKEN_USERNAME_CLAIM, user.getUsername()).
				claim(JWT_TOKEN_ROLE_CLAIM, user.getRole()).
				signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET_KEY.getBytes(JWT_TOKEN_ENCODING_TYPE)).
				compact();
	}
	
	public static AppUser parseToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		
		Claims claims = Jwts.parser().setSigningKey(JWT_TOKEN_SECRET_KEY.getBytes(JWT_TOKEN_ENCODING_TYPE)).parseClaimsJws(token).getBody();
		System.out.println("Expiration: " + claims.getExpiration());
		String role = claims.get(JWT_TOKEN_ROLE_CLAIM).toString();
		System.out.println("Role: " + role);
		Collection<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
		
		AppUser user = new AppUser(claims.get(JWT_TOKEN_USERNAME_CLAIM).toString(), BLANK_STR, role);
		
		user.setGrantedAuths(grantedAuths);
		
		return user;
		
	}
}
