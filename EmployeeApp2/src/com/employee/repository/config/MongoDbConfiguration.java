package com.employee.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoDbConfiguration {

	@Value("${mongo.db.dbname}")
	private String dbName;
	
	@Value("${mongo.db.port}")
	private Integer dbPort;
	
	@Value("${mongo.db.url.primary}")
	private String dbUrlPrimary;
	
	@Value("${mongo.db.url.secondary}")
	private String dbUrlSecondary;
	
	@Value("${mongo.db.url.secondary2}")
	private String dbUrlSecondary2;
	
	@Bean
	public DB getMongoDatabase(){
		
		MongoClientURI uri = new MongoClientURI(dbUrlPrimary);
		
		MongoClient mongoClient = new MongoClient(uri);
		DB database = mongoClient.getDB(dbName);
				
		return database;
	}
	
	/*public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(dbUrlPrimary), dbName);
	}

	@Bean
	public MongoTemplate getMongoTemplate() throws Exception{
		
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}*/
}
