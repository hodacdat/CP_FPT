package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CheckinDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Checkin;
import com.example.demo.entity.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.CheckinRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class CheckinService {

	@Autowired
	private CheckinRepository repository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public String save(CheckinDTO checkinDTO) {
		Patient p = null;
		Appointment app = null;
		Checkin ckn = null;

		if (checkinDTO.getIdC() == "") {
			checkinDTO.setIdC(null);
		}
		if (checkinDTO.getIdA() == "") {
			checkinDTO.setIdA(null);
		}

		// for user and booking
		if (checkinDTO.getIdC() != null && checkinDTO.getIdA() != null) {
			p = patientRepository.findByID(checkinDTO.getIdC());
			app = appointmentRepository.findByID(Integer.parseInt(checkinDTO.getIdA()));
			ckn = repository.findByAppId(checkinDTO.getIdA());

			if (ckn != null) {
				return "Appointment already checked in system";
			}

			if (p == null || app == null) {
				return "Patient or Appointment is not available in system";
			}
			if (checkinDTO.getDescription() != null) {
				if (checkinDTO.getDescription().length() >= 255) {
					return "Description length exceeds 255 characters.";
				}
			}
			Checkin ci = new Checkin(checkinDTO.getSpec(), checkinDTO.getSymtom(), checkinDTO.getBookPlace(),
					checkinDTO.getDescription(), checkinDTO.getDoctorId(), checkinDTO.getIdA(), checkinDTO.getName(),
					checkinDTO.getPhone(), checkinDTO.getBirthday(), checkinDTO.getGender(), p);
			repository.save(ci);
			return "success";

		}
		;

		// for user not book
		if (checkinDTO.getIdC() != null && checkinDTO.getIdA() == null) {
			p = patientRepository.findByID(checkinDTO.getIdC());

			if (p == null) {
				return "Patient is not available in system";
			}

			Checkin ci = new Checkin(checkinDTO.getSpec(), checkinDTO.getSymtom(), checkinDTO.getBookPlace(),
					checkinDTO.getDescription(), checkinDTO.getDoctorId(), checkinDTO.getName(), checkinDTO.getPhone(),
					checkinDTO.getBirthday(), checkinDTO.getGender(), p);
			repository.save(ci);
			return "success";

		}
		;

		// for guess booking
		if (checkinDTO.getIdC() == null && checkinDTO.getIdA() != null) {
			app = appointmentRepository.findByID(Integer.parseInt(checkinDTO.getIdA()));
			ckn = repository.findByAppId(checkinDTO.getIdA());

			if (ckn != null) {
				return "Appointment already checked in system";
			}
			if (app == null) {
				return "Appointment is not available in system";
			}

			Checkin ci = new Checkin(checkinDTO.getSpec(), checkinDTO.getSymtom(), checkinDTO.getBookPlace(),
					checkinDTO.getDescription(), checkinDTO.getDoctorId(), checkinDTO.getIdA(), checkinDTO.getName(),
					checkinDTO.getPhone(), checkinDTO.getBirthday(), checkinDTO.getGender());

			repository.save(ci);
			return "success";

		}
		;

		// for guess not book
		if (checkinDTO.getIdC() == null && checkinDTO.getIdA() == null) {
			Checkin ci = new Checkin(checkinDTO.getSpec(), checkinDTO.getSymtom(), checkinDTO.getBookPlace(),
					checkinDTO.getDescription(), checkinDTO.getDoctorId(), checkinDTO.getName(), checkinDTO.getPhone(),
					checkinDTO.getBirthday(), checkinDTO.getGender());
			repository.save(ci);
			return "success";
		}
		;
		return null;
	}

	public Checkin update(Checkin checkin) {
		Checkin c = repository.findById(checkin.getId())
				.orElseThrow(() -> new EntityNotFoundException("Checkin not found with id " + checkin.getId()));

		return repository.save(checkin);
	}

	public List<Checkin> findAll() {
		return repository.findAll();
	}

	public List<Checkin> findAllNotApprove() {
		return repository.getAllIncome();
	}

	public Optional<Checkin> findById(Integer id) {
		return repository.findById(id);
	}

	public List<Checkin> findByPaintedId(String paintedId) {
		return repository.findByPaintedId(paintedId);
	}

	public List<Checkin> findByDoctorId(String doctorId) {
		return repository.findByDoctorId(doctorId);
	}

	public List<Checkin> findIncomeByDoctorId(String doctorId) {
		return repository.getAllIncomeWithDoctorid(doctorId);
	}

	public void saveCheckin(Checkin appointment) {
		repository.save(appointment);
	}

	public int countCommandFlag2ForCurrentMonth() {
		return repository.countCommandFlag2ForCurrentMonth();
	}

	public int countCommandFlag3ForCurrentMonth() {
		return repository.countCommandFlag3ForCurrentMonth();
	}

	public List<Object[]> countCheckinsByDoctor() {
		return repository.countCheckinsByDoctor();
	}

	public List<Object[]> countCheckinsByDoctorAndSpecial() {
		return repository.countCheckinsByDoctorAndSpecial();
	}

	public Long countAppointmentByDoctor(String doctorId) {
		return repository.countAppointmentByDoctor(doctorId);
	}

}
