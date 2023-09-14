package com.example.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class PatientDTO {

	private String id;
	private String name;
	private String email;
	private String birthdate;
	private String address;
	private String gender;
	private String avatar;
	private String phone;

	public PatientDTO() {
		// TODO Auto-generated constructor stub
	}

	public PatientDTO(String id, String name, String email, String birthdate, String address, String gender,
			String avatar, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthdate = birthdate;
		this.address = address;
		this.gender = gender;
		this.avatar = avatar;
		this.phone = phone;

	}

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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	 public boolean isAvatarEmptyOrNull() {
	        String avatar = getAvatar();
	        return avatar == null || avatar.isEmpty();
	    }
}
