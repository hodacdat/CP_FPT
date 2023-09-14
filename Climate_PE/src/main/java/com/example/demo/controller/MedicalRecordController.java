package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.MedicalRecordDTO;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalrecord")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService service;

	@PostMapping("/save")
	public String save(@RequestBody MedicalRecord medicalRecord) {
		service.save(medicalRecord);
		return "success";
	}

	@GetMapping("/list")
	public List<MedicalRecord> getAll() {
		return service.findAll();
	}

	@GetMapping("/listByDoctorId")
	public List<MedicalRecord> getAllByDoctorId(@RequestParam("id") String id) {
		return service.findAllByDoctorId(id);
	}

	@GetMapping("/listByPatientId")
	public List<MedicalRecord> getAllByPatientId(@RequestParam("id") String id) {
		return service.findAllByPatientId(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable(value = "id") Integer id) {
		Optional<MedicalRecord> medicalRecords = service.findById(id);
		if (medicalRecords.isPresent()) {
			return ResponseEntity.ok().body(medicalRecords.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<String> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord) {
//	
		try {
			service.createMedicalRecord(medicalRecord);
			return ResponseEntity.ok("Medical record created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create medical record.");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable int id,
			@RequestBody MedicalRecord medicalRecord) {
		Optional<MedicalRecord> existingMedicalRecord = service.getMedicalRecordById(id);
		if (existingMedicalRecord.isPresent()) {
			medicalRecord.setId(id);
			MedicalRecord updatedMedicalRecord = service.updateMedicalRecord(medicalRecord);
			return ResponseEntity.ok(updatedMedicalRecord);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable int id) {
		Optional<MedicalRecord> existingMedicalRecord = service.getMedicalRecordById(id);
		if (existingMedicalRecord.isPresent()) {
			service.deleteMedicalRecord(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
