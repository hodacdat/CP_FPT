package com.example.demo.DTO;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834202981164216046L;
	private String id;
	private String name;
	private String email;
	private String password;
	private String birthdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public RegisterRequest() {
		// TODO Auto-generated constructor stub
	}
}
