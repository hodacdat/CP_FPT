package com.example.demo.mailHelper;

public class MailDetail {
	private String recipient;
	private String subject;
	private String msgBody;
	private String attachment;

	public MailDetail() {
	}

	public MailDetail(String recipient, String subject, String msgBody, String attachment) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.msgBody = msgBody;
		this.attachment = attachment;
	}

	public MailDetail(String recipient, String subject, String msgBody) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.msgBody = msgBody;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
