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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHEDULE_ID")
	private int id;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@Column(name = "EXAM_DATE")
	private String examDate;

	@Column(name = "EXAM_TIME")
	private String examTime;

	@Column(name = "RELEASE_TIME")
	private String releaseTime = timeNow();

	@OneToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name = "internal_id")
	private InternalAccount inaccounts;

	public Schedule() {
		// TODO Auto-generated constructor stub
	}

	// book - schedule

	public Schedule(String examDate, String examTime, Appointment appointment, InternalAccount inaccounts) {
		super();
		this.examDate = examDate;
		this.examTime = examTime;
		this.appointment = appointment;
		this.inaccounts = inaccounts;

		// 0: create, 1: complete, 2: cancel
		this.commandFlag = 0;
		this.releaseTime = timeNow();
	}

	// schedule for day off doctor

	public Schedule(String examDate, String examTime, InternalAccount inaccounts) {
		super();
		this.examDate = examDate;
		this.examTime = examTime;
		this.inaccounts = inaccounts;

		// 0: create, 1: complete, 2: cancel
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public InternalAccount getInaccounts() {
		return inaccounts;
	}

	public void setInaccounts(InternalAccount inaccounts) {
		this.inaccounts = inaccounts;
	}
}
