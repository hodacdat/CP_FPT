package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appointment")
public class Appointment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7657686804467804181L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPOINTMENT_ID")
	private int id;

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	private Patient patient;

	@Column(name = "REGISTER_TIME")
	private String registerTime;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "DOCTOR_NAME", nullable = false)
	private String doctorName;

	@Column(name = "EXAM_DATE", nullable = false)
	private String examDate;

	@Column(name = "EXAM_TIME", nullable = false)
	private String examTime;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "SPECIATLY")
	private String speciatly;

	@Column(name = "SYMPTOM")
	private String symptom;

	@Column(name = "PATIENT_NAME")
	private String patientName;

	@Column(name = "BIRTHDAY")
	private String birthday;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "BOOK_PLACE")
	private String bookPlace;

	@JsonIgnore
	@OneToOne(mappedBy = "appointment")
	private Schedule schedule;

	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	// for user

	public Appointment(Patient patient, String doctorName, String examDate, String examTime, String note,
			String speciatly, String symptom, String patientName, String birthday, String gender, String phone,
			String bookPlace) {
		super();
		this.patient = patient;
		this.doctorName = doctorName;
		this.examDate = examDate;
		this.examTime = examTime;
		this.note = note;
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.patientName = patientName;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.bookPlace = bookPlace;

		// 0: pending, 1: approve, 2: cancel
		this.commandFlag = 0;
		this.registerTime = timeNow();
	}

	// for guest
	public Appointment(String doctorName, String examDate, String examTime, String note, String speciatly,
			String symptom, String patientName, String birthday, String gender, String phone, String bookPlace) {
		super();
		this.doctorName = doctorName;
		this.examDate = examDate;
		this.examTime = examTime;
		this.note = note;
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.patientName = patientName;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.bookPlace = bookPlace;

		// 0: pending, 1: approve, 2: cancel
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSpeciatly() {
		return speciatly;
	}

	public void setSpeciatly(String speciatly) {
		this.speciatly = speciatly;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBookPlace() {
		return bookPlace;
	}

	public void setBookPlace(String bookPlace) {
		this.bookPlace = bookPlace;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient=" + patient + ", registerTime=" + registerTime + ", commandFlag="
				+ commandFlag + ", doctorName=" + doctorName + ", examDate=" + examDate + ", examTime=" + examTime
				+ ", note=" + note + ", speciatly=" + speciatly + ", symptom=" + symptom + ", patientName="
				+ patientName + ", birthday=" + birthday + ", gender=" + gender + ", phone=" + phone + ", bookPlace="
				+ bookPlace + ", schedule=" + schedule + "]";
	}

}
