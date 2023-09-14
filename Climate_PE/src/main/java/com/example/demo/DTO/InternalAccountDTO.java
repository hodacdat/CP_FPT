package com.example.demo.DTO;

import java.util.List;

import com.example.demo.entity.InternalAccount;

public class InternalAccountDTO {
	private String nameSepcial;
	private int count;
	private List<InternalAccount> doctorList;

	public InternalAccountDTO(String nameSepcial, int count, List<InternalAccount> doctorList) {
		this.count = count;
		this.doctorList = doctorList;
		this.nameSepcial = nameSepcial;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<InternalAccount> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(List<InternalAccount> doctorList) {
		this.doctorList = doctorList;
	}

	public String getNameSepcial() {
		return nameSepcial;
	}

	public void setNameSepcial(String nameSepcial) {
		this.nameSepcial = nameSepcial;
	}
}