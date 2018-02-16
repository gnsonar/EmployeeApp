package com.emp.demo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.employee.model.Employee;

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
		
		Employee e1 = new Employee();
		e1.setId(1322324l);
		
		Employee e2 = new Employee();
		e2.setId(1322325l);
		
		Employee e3 = new Employee();
		e3.setId(1322326l);
		
		List<Employee> list = Arrays.asList(e1,e2,e3);
		
		Optional<Employee> abc = list.stream().filter(e -> e.getId() == 1322326).findFirst();
		
		System.out.println(abc.get().getId());
		
	}
}
