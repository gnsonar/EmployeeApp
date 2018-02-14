package com.employee.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapper {

	public static String mapOnjectToJson(Object obj) throws JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(obj);
	}
}
