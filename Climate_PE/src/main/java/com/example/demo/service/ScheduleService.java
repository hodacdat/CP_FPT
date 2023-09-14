package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	public List<Schedule> findAll() {
		return repository.findAll();
	}

	public List<Schedule> findAllByInIdAndDate(int id, String date) {

		return repository.findAllByInIdAndDate(id, date);
	}

	public Optional<Schedule> findById(Integer id) {
		return repository.findById(id);
	}

	public Schedule findByAppId(int id) {
		return repository.findByAppId(id);
	}

	public Schedule saveSchedule(Schedule schedule) {
		return repository.save(schedule);

	}

	public Schedule update(Schedule schedule) {
		Schedule c = repository.findById(schedule.getId())
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found with id " + schedule.getId()));

		return repository.save(schedule);
	}

	public List<Schedule> getSortedSchedulesByEmail(String email) {
		return repository.findByInaccountsEmailOrderByExamDateAscExamTimeAsc(email);
	}
}
