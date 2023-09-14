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

import com.example.demo.DTO.AppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Schedule;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.InternalRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.ScheduleRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository repository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private InternalRepository internalRepository;

	public String save(AppointmentDTO appointmentDTO) {
		Appointment app = null;
		Appointment appSearch = null;
		Patient p = null;
		Schedule s = null;

		p = patientRepository.findByIDAndName(appointmentDTO.getIdC(), appointmentDTO.getName());
		p = patientRepository.findByID(appointmentDTO.getIdC());
		if (p == null) {
			return ("cannot find patient");
		}

		// check appointment
		appSearch = repository.findOne(p.getId(), appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		if (appSearch != null) {
			return "You already book appointment at this time";
		}

		// check schedule
		s = scheduleRepository.findByInNameAndDateTime(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		System.out.println(s);
		if (s != null) {
			return "This doctor is busy at this time";
		}

		app = new Appointment(p, appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime(), appointmentDTO.getDescription(), appointmentDTO.getSpec(),
				appointmentDTO.getSymtom(), appointmentDTO.getName(), appointmentDTO.getBirthday(),
				appointmentDTO.getGender(), appointmentDTO.getPhone(), appointmentDTO.getBookPlace());
		repository.save(app);
		return "success";

	}

	public String saveGuest(AppointmentDTO appointmentDTO) {
		Appointment app = null;
		Schedule s = null;

		// check schedule
		s = scheduleRepository.findByInNameAndDateTime(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		System.out.println(s);
		if (s != null) {
			return "This doctor is busy at this time.";
		}
		if (appointmentDTO.getDescription()!= null) {
			if (appointmentDTO.getDescription().length() >= 255) {
				return "Description length exceeds 255 characters.";
			}
		}
		app = new Appointment(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime(), appointmentDTO.getDescription(), appointmentDTO.getSpec(),
				appointmentDTO.getSymtom(), appointmentDTO.getName(), appointmentDTO.getBirthday(),
				appointmentDTO.getGender(), appointmentDTO.getPhone(), appointmentDTO.getBookPlace());
		repository.save(app);
		return "success";

	}

	public Appointment saveNow(AppointmentDTO appointmentDTO) {
		Appointment app = null;
		Appointment appSearch = null;
		Patient p = null;
		Schedule s = null;

		// check patient
		p = patientRepository.findByID(appointmentDTO.getIdC());
		if (p == null) {
			return null;
		}

		// check appointment
		appSearch = repository.findOne(p.getId(), appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		if (appSearch != null) {
			return null;
		}

		// check schedule
		s = scheduleRepository.findByInNameAndDateTime(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		System.out.println(s);
		if (s != null) {
			return null;
		}
		app = new Appointment(p, appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime(), appointmentDTO.getDescription(), appointmentDTO.getSpec(),
				appointmentDTO.getSymtom(), appointmentDTO.getName(), appointmentDTO.getBirthday(),
				appointmentDTO.getGender(), appointmentDTO.getPhone(), appointmentDTO.getBookPlace());
		repository.save(app);

		return app;

	}

	public Appointment saveGuestNow(AppointmentDTO appointmentDTO) {
		Appointment app = null;
		Schedule s = null;

		// check schedule
		s = scheduleRepository.findByInNameAndDateTime(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime());
		System.out.println(s);
		if (s != null) {
			return null;
		}

		app = new Appointment(appointmentDTO.getDoctorName(), appointmentDTO.getBookDate(),
				appointmentDTO.getBookTime(), appointmentDTO.getDescription(), appointmentDTO.getSpec(),
				appointmentDTO.getSymtom(), appointmentDTO.getName(), appointmentDTO.getBirthday(),
				appointmentDTO.getGender(), appointmentDTO.getPhone(), appointmentDTO.getBookPlace());
		repository.save(app);
		return app;

	}

	public Appointment update(AppointmentDTO appointment) {
		int id = Integer.parseInt(appointment.getIdA());
		Appointment c = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Appointment not found with id " + appointment.getIdA()));

		Patient p = null;
		if (appointment.getIdC() != null) {
			p = patientRepository.findByID(appointment.getIdC());

			if (p == null) {
				new EntityNotFoundException("Patient not found with id " + appointment.getIdC());
			}
		}

		c.setPatient(p);
		c.setPatientName(appointment.getName());
		c.setBirthday(appointment.getBirthday());
		c.setGender(appointment.getGender());
		c.setPhone(appointment.getPhone());
		c.setBookPlace(appointment.getBookPlace());
		c.setSymptom(appointment.getSymtom());
		c.setSpeciatly(appointment.getSpec());
		c.setDoctorName(appointment.getDoctorName());
		c.setExamDate(appointment.getBookDate());
		c.setExamTime(appointment.getBookTime());
		c.setNote(appointment.getDescription());

		InternalAccount i = internalRepository.findDoctorByName(appointment.getDoctorName());
		InternalAccount ic = i;
		// change to approved
		c.setCommandFlag(1);

		// add to schedule
		Schedule s = new Schedule(appointment.getBookDate(), appointment.getBookTime(), c, ic);
		scheduleRepository.save(s);

		return repository.save(c);
	}

	public List<Appointment> findAll() {
		return repository.findAll();
	}

	public List<Appointment> findAllNotApprove() {
		return repository.getAllIncome();
	}

	public Optional<Appointment> findById(Integer id) {
		return repository.findById(id);
	}

	public List<Appointment> findByPaintedId(String paintedId) {
		return repository.findByPaintedId(paintedId);
	}

	public void saveAppointment(Appointment appointment) {
		repository.save(appointment);
	}
}
