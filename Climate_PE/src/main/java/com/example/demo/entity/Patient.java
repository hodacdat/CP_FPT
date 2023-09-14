package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "patient")
public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PAINTED_ID") // cccd
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD", columnDefinition = "LONGTEXT")
	private String password;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "BIRTHDATE")
	private String birthDate;

	@Column(name = "REGISTRATION_TIME")
	private String registrationTime = timeNow();;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "ROLE")
	private String role;

	@JsonIgnore
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments;

	@JsonIgnore
	@OneToMany(mappedBy = "patient")
	private List<Checkin> checkins;

	/////////////////////////////////

	public Patient() {
		// constructor mặc định không có tham số
	}

	public Patient(String id, String name, String email, String password, String birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;

		// 0: create, 1: verify, 2: block
		this.commandFlag = 0;
		this.registrationTime = timeNow();
		this.role = "USER";
	}

	// for update
	public Patient(String id, String name, String email, String address, String phone, String gender, String avatar,
			String birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.avatar = avatar;
		this.birthDate = birthDate;
	}

	public Patient(String id, String name, String email, String password, String birthDate, String avatarDefault) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;

		// 0: create, 1: verify, 2: block
		this.commandFlag = 0;
		this.registrationTime = timeNow();
		this.role = "USER";
		this.avatar = avatarDefault;

	}

	public String timeNow() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Apply GMT +7 offset
		ZoneOffset offset = ZoneOffset.ofHours(7);
		LocalDateTime gmtPlus7DateTime = currentDateTime.plusHours(7).atOffset(offset).toLocalDateTime();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
		String formattedDateTime = gmtPlus7DateTime.format(formatter);
		return formattedDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(String registrationTime) {
		this.registrationTime = registrationTime;
	}

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Checkin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<Checkin> checkins) {
		this.checkins = checkins;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", address="
				+ address + ", phone=" + phone + ", gender=" + gender + ", avatar=" + avatar + ", birthDate="
				+ birthDate + ", registrationTime=" + registrationTime + ", commandFlag=" + commandFlag + ", role="
				+ role + "]";
	}

}