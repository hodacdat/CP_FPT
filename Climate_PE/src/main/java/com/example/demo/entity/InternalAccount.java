package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "internal_account")
public class InternalAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INTERNAL_ID")
	private int id;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", columnDefinition = "LONGTEXT")
	private String password;

	@Column(name = "NAME")
	private String name;

	@Column(name = "BIRTHDATE")
	private String birthDate;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "YEAR_OF_EXP")
	private int yearOfExp;

	@Column(name = "EDUCATION")
	private String education;

	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "INTRODUCT")
	private String introduct;

	@Column(name = "REGISTER_TIME")
	private String registerTime = timeNow();

	@ManyToOne
	@JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION_ID")
	private Location workingPlace;

	@ManyToOne
	@JoinColumn(name = "ROLE", referencedColumnName = "ROLE_ID")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "SPECIATLY", referencedColumnName = "SPEC_ID")
	private Specialty specialty;

	@JsonIgnore
	@OneToMany(mappedBy = "inaccounts")
	private List<Schedule> schedule;

/////////////////////////////////

	public InternalAccount() {
		// constructor mặc định không có tham số
	}

	public InternalAccount(String email, String password, String name, String birthDate, String gender, String phone,
			int yearOfExp, String education, Location workingPlace, Role role, Specialty specialty) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.yearOfExp = yearOfExp;
		this.education = education;
		this.workingPlace = workingPlace;
		this.role = role;
		this.specialty = specialty;

		// 0: create, 1: block
		this.commandFlag = 0;
		this.registerTime = timeNow();
	}
	public InternalAccount(String email, String password, String name, Location workingPlace, Role role, Specialty specialty) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.workingPlace = workingPlace;
		this.role = role;
		this.specialty = specialty;

		// 0: create, 1: block
		this.commandFlag = 0;
		this.registerTime = timeNow();
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	public void setYearOfExp(int yearOfExp) {
		this.yearOfExp = yearOfExp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getYearOfExp() {
		return yearOfExp;
	}

	public void setYearOfExp(Integer yearOfExp) {
		this.yearOfExp = yearOfExp;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public Location getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(Location workingPlace) {
		this.workingPlace = workingPlace;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getIntroduct() {
		return introduct;
	}

	public void setIntroduct(String introduct) {
		this.introduct = introduct;
	}

	@Override
	public String toString() {
		return "InternalAccount [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", birthDate=" + birthDate + ", gender=" + gender + ", phone=" + phone + ", yearOfExp=" + yearOfExp
				+ ", education=" + education + ", avatar=" + avatar + ", commandFlag=" + commandFlag + ", introduct="
				+ introduct + ", registerTime=" + registerTime + ", workingPlace=" + workingPlace + ", role=" + role
				+ ", specialty=" + specialty + ", schedule=" + schedule + "]";
	}
	 public boolean isAvatarEmptyOrNull() {
	        String avatar = getAvatar();
	        return avatar == null || avatar.isEmpty();
	    }

}
