package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class NewsDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "CONTENT", length = 90)
	private String content;

	@Column(name = "TITLE", length = 90)
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IMAGE")
	private String image;

	@Min(value = 0, message = "Rate must be at least 0")
	@Max(value = 5, message = "Rate must be at most 5")
	@Column(name = "RATE")
	private int rate;

	@Column(name = "LIKE_COUNT")
	private int like;

	@Column(name = "DISLIKE_COUNT")
	private int dislike;

	@Column(name = "SPECIATLY")
	private String specialty;

	@Column(name = "CREATEBY")
	private String creatby;
	// Constructors, getters, and setters

	public NewsDetail() {
	}

	public NewsDetail(String content, String title, String description, String image, int rate, int like, int dislike) {
		this.content = content;
		this.title = title;
		this.description = description;
		this.image = image;
		setRate(rate); // Call the setter to validate the rate value
		this.like = like;
		this.dislike = dislike;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getCreatby() {
		return creatby;
	}

	public void setCreatby(String creatby) {
		this.creatby = creatby;
	}

}