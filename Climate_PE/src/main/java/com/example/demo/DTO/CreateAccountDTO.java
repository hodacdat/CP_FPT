package com.example.demo.DTO;

import com.example.demo.entity.Role;

public class CreateAccountDTO {
	private String id;
	private String name;
	private String email;
	private String password;
	private String role;
	private String specialty;
	private String location;
	private String avatar;
	private String yearOfExp;
	private String introduct;
	private String education;
	private String birthdate;
	private String gender;
	private String phone;
	private String commandFlag;

	public CreateAccountDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getCommandFlag() {
		return commandFlag;
	}
	public void setCommandFlag(String commandFlag) {
		this.commandFlag = commandFlag;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getYearOfExp() {
		return yearOfExp;
	}

	public void setYearOfExp(String yearOfExp) {
		this.yearOfExp = yearOfExp;
	}

	public String getIntroduct() {
		return introduct;
	}

	public void setIntroduct(String introduct) {
		this.introduct = introduct;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CreateAccountDTO(String name, String email, String password, String role, String specialty, String location,
			String avatar) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.specialty = specialty;
		this.location = location;
		this.avatar = avatar;
	}

	public CreateAccountDTO(String id, String name, String email, String password, String role, String specialty,
			String location, String avatar, String yearOfExp, String introduct, String education, String birthdate,
			String gender, String phone, String commandFlag) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.specialty = specialty;
		this.location = location;
		this.avatar = avatar;
		this.yearOfExp = yearOfExp;
		this.introduct = introduct;
		this.education = education;
		this.birthdate = birthdate;
		this.gender = gender;
		this.phone = phone;
		this.commandFlag = commandFlag;
	}
	
	

}
