package com.employee.dto;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeDTO {

	private long id;
	
	@NotEmpty
	@Length(min=1, max = 20)
	private String firstName;
	
	@NotEmpty
	@Length(min=1, max = 20)
	private String lastName;
	
	@NotEmpty
	@Length(min=5, max = 50)
	@Email
	private String email;
	
	private boolean highlight;
	private String userCreated;
	
	
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public String getUser() {
		return userCreated;
	}
	public void setUser(String user) {
		this.userCreated = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
}
