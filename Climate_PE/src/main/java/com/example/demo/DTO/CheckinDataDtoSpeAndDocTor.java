package com.example.demo.DTO;

public class CheckinDataDtoSpeAndDocTor {
	private String id;
	private String idspe;
	private String nameDoctor;
	private String nameSepcial;
	private Long online;
	private Long examination;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameSepcial() {
		return nameSepcial;
	}
	public void setNameSepcial(String nameSepcial) {
		this.nameSepcial = nameSepcial;
	}
	public Long getOnline() {
		return online;
	}
	public void setOnline(Long online) {
		this.online = online;
	}
	public Long getExamination() {
		return examination;
	}
	public void setExamination(Long examination) {
		this.examination = examination;
	}
	public String getIdspe() {
		return idspe;
	}
	public void setIdspe(String idspe) {
		this.idspe = idspe;
	}
	public String getNameDoctor() {
		return nameDoctor;
	}
	public void setNameDoctor(String nameDoctor) {
		this.nameDoctor = nameDoctor;
	}
	public CheckinDataDtoSpeAndDocTor(String id, String idspe, String nameDoctor, String nameSepcial, Long online,
			Long examination) {
		super();
		this.id = id;
		this.idspe = idspe;
		this.nameDoctor = nameDoctor;
		this.nameSepcial = nameSepcial;
		this.online = online;
		this.examination = examination;
	}
	public CheckinDataDtoSpeAndDocTor() {
		// TODO Auto-generated constructor stub
	}
}
