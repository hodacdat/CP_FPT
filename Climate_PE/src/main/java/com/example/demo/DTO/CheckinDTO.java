package com.example.demo.DTO;

public class CheckinDTO {

	private String name;
	private String phone;
	private String idC;
	private String idA;
	private String birthday;
	private String gender;
	private String bookPlace;
	private String symtom;
	private String spec;
	private String doctorId;
	private String bookDate;
	private String description;
	private String paitent_name;
	private String register_time;

	public CheckinDTO() {
		// TODO Auto-generated constructor stub
	}

	public CheckinDTO(String name, String phone, String idC, String idA, String birthday, String gender,
			String bookPlace, String symtom, String spec, String doctorId, String bookDate, String description,
			String paitent_name, String register_time) {
		super();
		this.name = name;
		this.phone = phone;
		this.idC = idC;
		this.idA = idA;
		this.birthday = birthday;
		this.gender = gender;
		this.bookPlace = bookPlace;
		this.symtom = symtom;
		this.spec = spec;
		this.doctorId = doctorId;
		this.bookDate = bookDate;
		this.description = description;
		this.paitent_name = paitent_name;
		this.register_time = register_time;
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

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
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

	public String getIdA() {
		return idA;
	}

	public void setIdA(String idA) {
		this.idA = idA;
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
