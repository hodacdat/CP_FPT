package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Schedule;
import com.example.demo.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class ScheduleController {

	@Autowired
	private ScheduleService service;

	@GetMapping("/list")
	public List<Schedule> getAll() {
		return service.findAll();
	}

	@GetMapping("/list_date/{id}")
	public List<Schedule> getScheduleByIdandMail(@PathVariable("id") int id,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd") String date) {
		System.out.println("- " + id);
		System.out.println("- " + date);
		return service.findAllByInIdAndDate(id, date);
	}

	@GetMapping("/listschedules")
	public List<Schedule> getSchedulesByEmailSortedByExamDate(@RequestParam("email") String email) {
		return service.getSortedSchedulesByEmail(email);
	}

	@PostMapping("/save")
	public String save(@RequestBody Schedule schedule) {
		service.saveSchedule(schedule);
		return "success";
	}

	@PutMapping("/update")
	public String update(@RequestBody Schedule schedule) {
		service.update(schedule);
		return "success";
	}

	@GetMapping("/list_schedules")
	public Optional<Schedule> getScheduleById(@RequestParam("id") int id) {
		return service.findById(id);
	}

}
