package com.example.demo.DTO;

public class AppointmentDTO {

	private String name;
	private String phone;
	private String idC;
	private String idA;
	private String birthday;
	private String gender;
	private String bookPlace;
	private String symtom;
	private String spec;
	private String doctorName;
	private String bookDate;
	private String bookTime;
	private String description;
	private String paitent_name;
	private String register_time;
	private String email;

	public AppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

//for user
	public AppointmentDTO(String name, String phone, String idC, String birthday, String gender, String bookPlace,
			String symtom, String spec, String doctorName, String bookDate, String bookTime, String description,
			String paitent_name, String register_time) {
		super();
		this.name = name;
		this.phone = phone;
		this.idC = idC;
		this.birthday = birthday;
		this.gender = gender;
		this.bookPlace = bookPlace;
		this.symtom = symtom;
		this.spec = spec;
		this.doctorName = doctorName;
		this.bookDate = bookDate;
		this.bookTime = bookTime;
		this.description = description;
		this.paitent_name = paitent_name;
		this.register_time = register_time;
	}

	public AppointmentDTO(String name, String phone, String idC, String idA, String birthday, String gender,
			String bookPlace, String symtom, String spec, String doctorName, String bookDate, String bookTime,
			String description, String paitent_name, String register_time) {
		super();
		this.phone = phone;
		this.idC = idC;
		this.idA = idA;
		this.birthday = birthday;
		this.gender = gender;
		this.bookPlace = bookPlace;
		this.symtom = symtom;
		this.spec = spec;
		this.doctorName = doctorName;
		this.bookDate = bookDate;
		this.bookTime = bookTime;
		this.description = description;
		this.paitent_name = paitent_name;
		this.register_time = register_time;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdA() {
		return idA;
	}

	public void setIdA(String idA) {
		this.idA = idA;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBookPlace() {
		return bookPlace;
	}

	public void setBookPlace(String bookPlace) {
		this.bookPlace = bookPlace;
	}

	public String getSymtom() {
		return symtom;
	}

	public void setSymtom(String symtom) {
		this.symtom = symtom;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdC() {
		return idC;
	}

	public void setIdC(String idC) {
		this.idC = idC;
	}

	public void setPaitent_name(String paitent_name) {
		this.paitent_name = paitent_name;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getPaitent_name() {
		return paitent_name;
	}

	public String getRegister_time() {
		return register_time;
	}

}
