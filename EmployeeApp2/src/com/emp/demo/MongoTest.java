package com.emp.demo;

import java.net.UnknownHostException;

import org.bson.Document;

import com.employee.model.Employee;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class MongoTest {

	public static void main(String[] args) throws UnknownHostException {
		
		MongoClientURI uri = new MongoClientURI(
				   "mongodb://kay:admin@cluster0-shard-00-00-4ooku.mongodb.net:27017,cluster0-shard-00-01-4ooku.mongodb.net:27017,cluster0-shard-00-02-4ooku.mongodb.net:27017/admin?ssl=true&replicaSet=cluster0-shard-0&authSource=admin");

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("test");
		
		MongoCollection<Document> employeeCollection = database.getCollection("empCollection");
		
		DB database1 = mongoClient.getDB("test");
		DBCollection collection = database1.getCollection("testcollection");
		
		String json = "{   'name' : 'gautam' , " +
				"'website' : 'demo.com' , " +
				"'EndTime' : null , " +
				"'address' : { 	'addressLine1' : 'Some address' , " +
						"'addressLine2' : 'Karol Bagh' , " +
						"'addressLine3' : 'New Delhi, India'}" +
				"}";
		DBObject dbObject = (DBObject)JSON.parse(json);
		collection.insert(dbObject);

	}
}
