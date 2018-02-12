package com.emp.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.client.RestTemplate;

public class ServiceConsumer {

	public static void main(String[] args) {
		
		String data = "{\"firstName\":\"fdgdfg\",\"lastName\":\"dfgdf\",\"email\":\"dfg@sdfsd.ggg\",\"id\":\"0\"}";
		
		URL url;
		
		RestTemplate template = new RestTemplate();
		System.out.println(template.getForObject("http://localhost:8080/EmployeeApp/employees/getEmployee/1234", String.class));
		
		
		try {
			
			url = new URL("http://localhost:8080/EmployeeApp/employees/saveEmployee");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type" ,"application/json");
			conn.setRequestProperty("Accept" ,"text/plain");
			
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			
			System.out.println(conn.getResponseCode());
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output = "";
			while((output = br.readLine()) != null){
				System.out.println(output);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
