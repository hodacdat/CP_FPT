package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.AppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Schedule;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.InternalService;
import com.example.demo.service.PatientService;
import com.example.demo.service.ScheduleService;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private InternalService internalService;

	@PostMapping("/save")
	public String save(@RequestBody AppointmentDTO appointmentDTO) {
		System.out.println(appointmentDTO.getIdC());

		// validate
		if (appointmentDTO.getName() == "" || appointmentDTO.getPhone() == "" || appointmentDTO.getBirthday() == ""
				|| appointmentDTO.getGender() == "" || appointmentDTO.getBookPlace() == ""
				|| appointmentDTO.getSpec() == "" || appointmentDTO.getDoctorName() == ""
				|| appointmentDTO.getBookDate() == "" || appointmentDTO.getBookTime() == ""
				|| appointmentDTO.getIdC() == "") {
			return "Invalid data, please fill all data";
		}

		String result = appointmentService.save(appointmentDTO);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}
	}

	@PostMapping("/saveguest")
	public String saveGuest(@RequestBody AppointmentDTO appointmentDTO) {

		// validate
		if (appointmentDTO.getName() == "" || appointmentDTO.getPhone() == "" || appointmentDTO.getBirthday() == ""
				|| appointmentDTO.getGender() == "" || appointmentDTO.getBookPlace() == ""
				|| appointmentDTO.getSpec() == "" || appointmentDTO.getDoctorName() == ""
				|| appointmentDTO.getBookDate() == "" || appointmentDTO.getBookTime() == "") {
			return "Invalid data, please fill all data";
		}

		String result = appointmentService.saveGuest(appointmentDTO);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}
	}

	@PutMapping("/update")
	public String update(@RequestBody AppointmentDTO appDTO) {
		System.out.println(appDTO.toString());
		appointmentService.update(appDTO);
		return "success";
	}

	@GetMapping("/list")
	public List<Appointment> getAll() {
		return appointmentService.findAll();
	}

	@GetMapping("/listBypaintedId")
	public List<Appointment> getByPaintedId(@RequestParam("painted_id") String paintedId) {
		return appointmentService.findByPaintedId(paintedId);
	}

	@GetMapping("/listIncome")
	public List<Appointment> getAllIncome() {
		return appointmentService.findAllNotApprove();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable(value = "id") Integer id) {
		Optional<Appointment> appointment = appointmentService.findById(id);
		if (appointment.isPresent()) {
			return ResponseEntity.ok().body(appointment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/bookapprove")
	public String saveApprove(@RequestBody AppointmentDTO appointmentDTO) {
		System.out.println(appointmentDTO.getIdC());

		// validate
		if (appointmentDTO.getName() == "" || appointmentDTO.getPhone() == "" || appointmentDTO.getBirthday() == ""
				|| appointmentDTO.getGender() == "" || appointmentDTO.getBookPlace() == ""
				|| appointmentDTO.getSpec() == "" || appointmentDTO.getDoctorName() == ""
				|| appointmentDTO.getBookDate() == "" || appointmentDTO.getBookTime() == ""
				|| appointmentDTO.getIdC() == "") {
			return "Invalid data, please fill all data";
		}

		Appointment result = null;
		result = appointmentService.saveNow(appointmentDTO);
		if (result != null) {
			result.setCommandFlag(1);
			Schedule schedule = new Schedule();
			schedule.setExamDate(result.getExamDate());
			schedule.setExamTime(result.getExamTime());
			schedule.setReleaseTime(result.timeNow());
			schedule.setCommandFlag(result.getCommandFlag());
			schedule.setAppointment(result);
			InternalAccount inter = internalService.findByName(result.getDoctorName(), result.getBookPlace());
			schedule.setInaccounts(inter);
			scheduleService.saveSchedule(schedule);
			return "success";
		} else {
			return "Has error";
		}
	}

	@PostMapping("/bookapproveguest")
	public String saveApproveGuest(@RequestBody AppointmentDTO appointmentDTO) {
		System.out.println(appointmentDTO.getIdC());

		// validate
		if (appointmentDTO.getName() == "" || appointmentDTO.getPhone() == "" || appointmentDTO.getBirthday() == ""
				|| appointmentDTO.getGender() == "" || appointmentDTO.getBookPlace() == ""
				|| appointmentDTO.getSpec() == "" || appointmentDTO.getDoctorName() == ""
				|| appointmentDTO.getBookDate() == "" || appointmentDTO.getBookTime() == "") {
			return "Invalid data, please fill all data";
		}

		Appointment result = null;
		result = appointmentService.saveGuestNow(appointmentDTO);
		if (result != null) {
			result.setCommandFlag(1);
			Schedule schedule = new Schedule();
			schedule.setExamDate(result.getExamDate());
			schedule.setExamTime(result.getExamTime());
			schedule.setReleaseTime(result.timeNow());
			schedule.setCommandFlag(0);
			schedule.setAppointment(result);
			InternalAccount inter = internalService.findByName(result.getDoctorName(), result.getBookPlace());
			schedule.setInaccounts(inter);
			scheduleService.saveSchedule(schedule);
			return "success";
		} else {
			return "Has error";
		}
	}

	@PutMapping("/commandFlag")
	public ResponseEntity<String> updateCommandFlag(@RequestParam("appointmentId") int appointmentId,
			@RequestParam("command") String command) {
		try {
			// Get the appointment by its ID
			Optional<Appointment> optionalAppointment = appointmentService.findById(appointmentId);

			if (optionalAppointment.isPresent()) {
				Appointment appointment = optionalAppointment.get();

				// Update the commandFlag based on the input
				if (command.equalsIgnoreCase("cancel")) {
					appointment.setCommandFlag(2);
				} else if (command.equalsIgnoreCase("approve")) {
					appointment.setCommandFlag(1);
					Schedule scheduleCheck = new Schedule();
					scheduleCheck = scheduleService.findByAppId(appointmentId);

					if (scheduleCheck != null) {
						return ResponseEntity.ok("This appointment already in schedule of doctor.");
					}

					Schedule schedule = new Schedule();
					schedule.setExamDate(appointment.getExamDate());
					schedule.setExamTime(appointment.getExamTime());
					schedule.setReleaseTime(appointment.timeNow());
					schedule.setCommandFlag(0);
					schedule.setAppointment(appointment);
					InternalAccount inter = internalService.findByName(appointment.getDoctorName(),
							appointment.getBookPlace());
					schedule.setInaccounts(inter);
					scheduleService.saveSchedule(schedule);
				} else {
					return ResponseEntity.badRequest().body("Invalid command.");
				}

				// Save the updated appointment
				appointmentService.saveAppointment(appointment);

				return ResponseEntity.ok("CommandFlag updated successfully.");
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	@PutMapping("/updatepaidforapp")
	public ResponseEntity<String> updatePatientID(@RequestParam("appointmentId") String appointmentId,
			@RequestParam("email") String email) {
		System.out.println(appointmentId);
		System.out.println(email);
		int appid = Integer.parseInt(appointmentId);
		try {
			// Get the appointment by its ID
			Optional<Appointment> optionalAppointment = appointmentService.findById(appid);
			if (optionalAppointment.isPresent()) {
				Appointment appointment = optionalAppointment.get();
				// Update the commandFlag based on the input
				if (appointment.getPatient() != null) {
					return ResponseEntity.badRequest().body("This appointment already belongs to someone else.");
				} else {
					Patient p = patientService.findByEmail(email);
//					System.out.println(p);
					if (p != null) {
						appointment.setPatient(p);
					} else {
						return ResponseEntity.badRequest().body("Cannot find patient, please try again.");
					}

					// Save the updated appointment
					appointmentService.saveAppointment(appointment);

					return ResponseEntity.ok("Updated successfully.");
				}
			} else {
				return ResponseEntity.badRequest().body("Cannot find appointment, please try again.");
			}
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}
}