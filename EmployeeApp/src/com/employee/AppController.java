package com.employee;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

	@RequestMapping(value = "/" , method = RequestMethod.GET , produces = MediaType.TEXT_HTML_VALUE)
	public String openPage(){
		return "redirect:/index.html";
	}
}
