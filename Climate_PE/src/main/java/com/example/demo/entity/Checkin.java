package com.example.demo.entity;

import java.time.LocalDate;
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
@Table(name = "checkin")
public class Checkin {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7657686804467804181L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHECKIN_ID")
	private int id;
	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "REGISTER_TIME")
	private String registerTime = timeNow();

	@Column(name = "EXAM_DATE")
	private String examDate;

	@Column(name = "SPECIATLY")
	private String speciatly;

	@Column(name = "SYMPTOM")
	private String symptom;

	@Column(name = "BOOK_PLACE")
	private String bookPlace;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "DOCTOR_ID")
	private String doctorId;

	@Column(name = "APPOINTMENT_ID")
	private String appointmentId;

	@Column(name = "PATIENT_NAME")
	private String patientName;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "BIRTHDAY")
	private String birthday;

	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "MONEY")
	private int money = 300000;

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	private Patient patient;

	@JsonIgnore
	@OneToOne(mappedBy = "checkin")
	private MedicalRecord medicalRecord;

	public Checkin() {
		// TODO Auto-generated constructor stub
	}

	// for user not booking

	public Checkin(String speciatly, String symptom, String bookPlace, String note, String doctorId, String patientName,
			String phone, String birthday, String gender, Patient patient) {
		super();
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.bookPlace = bookPlace;
		this.note = note;
		this.doctorId = doctorId;
		this.patientName = patientName;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
		this.patient = patient;

		// 0: create, 1: examining, 2: complete, 3: cancel
		this.commandFlag = 0;
		this.examDate = dateNow();
		this.registerTime = timeNow();
	}

	// for not have acc and not booking

	public Checkin(String speciatly, String symptom, String bookPlace, String note, String doctorId, String patientName,
			String phone, String birthday, String gender) {
		super();
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.bookPlace = bookPlace;
		this.note = note;
		this.doctorId = doctorId;
		this.patientName = patientName;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;

		// 0: create, 1: examining, 2: complete, 3: cancel
		this.commandFlag = 0;
		this.examDate = dateNow();
		this.registerTime = timeNow();
	}

	// for user booking
	public Checkin(String speciatly, String symptom, String bookPlace, String note, String doctorId,
			String appointmentId, String patientName, String phone, String birthday, String gender, Patient patient) {
		super();
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.bookPlace = bookPlace;
		this.note = note;
		this.doctorId = doctorId;
		this.appointmentId = appointmentId;
		this.patientName = patientName;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
		this.patient = patient;

		// 0: create, 1: examining, 2: complete, 3: cancel
		this.commandFlag = 0;
		this.examDate = dateNow();
		this.registerTime = timeNow();
	}

	// for not have acc booking
	public Checkin(String speciatly, String symptom, String bookPlace, String note, String doctorId,
			String appointmentId, String patientName, String phone, String birthday, String gender) {
		super();
		this.speciatly = speciatly;
		this.symptom = symptom;
		this.bookPlace = bookPlace;
		this.note = note;
		this.doctorId = doctorId;
		this.appointmentId = appointmentId;
		this.patientName = patientName;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;

		// 0: create, 1: examining, 2: complete, 3: cancel
		this.commandFlag = 0;
		this.examDate = dateNow();
		this.registerTime = timeNow();
	}

	public String dateNow() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formattedDate = today.format(formatter);
		return formattedDate;
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

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
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

	public String getBookPlace() {
		return bookPlace;
	}

	public void setBookPlace(String bookPlace) {
		this.bookPlace = bookPlace;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

}
