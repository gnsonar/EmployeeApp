package com.employee.model;


public class Employee {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String user;
	
	private byte[] photo;
	
	public Employee(){}
	public Employee(String fname, String lname, String email) {
		this.firstName = fname;
		this.lastName = lname;
		this.email = email;
	}
	
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Id:" +this.getId() + "fname:"+this.getFirstName() + " lname:"+this.getLastName() + " Email:"+ this.getEmail() + " photo:"+this.getPhoto();
	}
	
}
