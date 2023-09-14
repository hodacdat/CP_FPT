package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medicalrecord")
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEDICAL_RECORD_ID")
	private int id;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "RELEASE_TIME")
	private String releaseTime = timeNow();

	@Column(name = "DOCTOR_ID")
	private String doctorId;

	@Column(name = "CLINIC_PROCESS", columnDefinition = "LONGTEXT")
	private String clinicProcess;

	@Column(name = "SUMARY_RESULT", columnDefinition = "LONGTEXT")
	private String sumaryResult;

	@Column(name = "TREATMENT", columnDefinition = "LONGTEXT")
	private String treatment;

	@JoinColumn(name = "CHECKIN_ID", referencedColumnName = "CHECKIN_ID")
	@OneToOne
	private Checkin checkin;

	public MedicalRecord() {
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(String doctorId, String clinicProcess, String sumaryResult, String treatment,
			Checkin checkin) {
		super();
		this.doctorId = doctorId;
		this.clinicProcess = clinicProcess;
		this.sumaryResult = sumaryResult;
		this.treatment = treatment;
		this.checkin = checkin;

		// 0: create, 1: block
		this.commandFlag = 0;
		this.releaseTime = timeNow();
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

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getClinicProcess() {
		return clinicProcess;
	}

	public void setClinicProcess(String clinicProcess) {
		this.clinicProcess = clinicProcess;
	}

	public String getSumaryResult() {
		return sumaryResult;
	}

	public void setSumaryResult(String sumaryResult) {
		this.sumaryResult = sumaryResult;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public Checkin getCheckin() {
		return checkin;
	}

	public void setCheckin(Checkin checkin) {
		this.checkin = checkin;
	}

}
