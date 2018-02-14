package com.employee;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.employee.model.Employee;
import com.employee.service.LoginService;

@Controller
//@ComponentScan(basePackages = "", excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, value = LoginService.class)})
public class AppController {

	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = MediaType.TEXT_HTML_VALUE)
	public String openPage(){
		return "redirect:/index.html";
	}
}
