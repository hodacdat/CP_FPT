package com.example.demo.DTO;

public class PatientData {
	private String email;
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PatientData(String email, String displayName) {
		super();
		this.email = email;
		this.displayName = displayName;
	}
	public PatientData() {
        // Default constructor
    }

}
