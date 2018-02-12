package com.employee.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.util.JacksonObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	/*@Autowired
	MongoTemplate mongoTemplate;*/
	
	@Autowired
	DB db;
	
	@Override
	public boolean addEmployee(Employee emp) throws JsonProcessingException {
		//mongoTemplate.insert(emp);
		
		DBCollection collection = db.getCollection("empCollection");
		
		DBObject obj = (DBObject) JSON.parse(JacksonObjectMapper.mapOnjectToJson(emp));
		collection.insert(obj);
		
		return true;
	}

	
	

}
