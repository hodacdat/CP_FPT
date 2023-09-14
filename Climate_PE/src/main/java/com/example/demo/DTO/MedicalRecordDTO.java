package com.example.demo.DTO;

public class MedicalRecordDTO {

	private int checkinId;
	private String clinicProcess;
	private String doctorId;
	private String releaseDate;
	private String sumaryResult;
	private String treatment;

	public MedicalRecordDTO() {
		// TODO Auto-generated constructor stub
	}

	public MedicalRecordDTO(int checkinId, String clinicProcess, String doctorId, String releaseDate,
			String sumaryResult, String treatment) {
		super();
		this.checkinId = checkinId;
		this.clinicProcess = clinicProcess;
		this.doctorId = doctorId;
		this.releaseDate = releaseDate;
		this.sumaryResult = sumaryResult;
		this.treatment = treatment;
	}

	public int getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(int checkinId) {
		this.checkinId = checkinId;
	}

	public String getClinicProcess() {
		return clinicProcess;
	}

	public void setClinicProcess(String clinicProcess) {
		this.clinicProcess = clinicProcess;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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

}
