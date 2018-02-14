package com.emp.demo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Demo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		map.put(12, "12");
		map.put(13, "13");
		
		System.out.println(map);
		
		map.put(13, "14");
		
		System.out.println(map);
		
		Calendar cal = Calendar.getInstance();
		
		System.out.println(cal.getTime());
		cal.add(Calendar.DATE, 2);
		System.out.println(cal.getTime());
		
	}
}
