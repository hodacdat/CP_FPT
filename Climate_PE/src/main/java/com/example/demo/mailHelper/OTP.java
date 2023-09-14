package com.example.demo.mailHelper;

public class OTP {
	private String email;
	private String content;
	private long expiredTime;

	public OTP() {
		// TODO Auto-generated constructor stub
	}

	public OTP(String email, String content, long expiredTime) {
		super();
		this.email = email;
		this.content = content;
		this.expiredTime = expiredTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

}
