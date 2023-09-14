package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.MedicalRecordDTO;
import com.example.demo.entity.Checkin;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository repository;

	@Autowired
	private CheckinService checkinService;

	@Autowired
	private InternalService internalService;

	public MedicalRecord save(MedicalRecord medicalRecord) {
		return repository.save(medicalRecord);
	}

	public List<MedicalRecord> findAll() {
		return repository.findAll();
	}

	public List<MedicalRecord> findAllByDoctorId(String id) {
		return repository.findAllByDoctorId(id);
	}

	public List<MedicalRecord> findAllByPatientId(String id) {
		return repository.findAllByPatientId(id);
	}

	public Optional<MedicalRecord> findById(Integer id) {
		return repository.findById(id);
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		return repository.findAll();
	}

	public Optional<MedicalRecord> getMedicalRecordById(int id) {
		return repository.findById(id);
	}

	public MedicalRecord findMedicalRecordByCheckinId(int checkinId) {
		return repository.findByCheckinId(checkinId);
	}

	public String timeNow() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Apply GMT +7 offset
		ZoneOffset offset = ZoneOffset.ofHours(7);
		LocalDateTime gmtPlus7DateTime = currentDateTime.plusHours(7).atOffset(offset).toLocalDateTime();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
		String formattedDateTime = gmtPlus7DateTime.format(formatter);
		return formattedDateTime;
	}

	public MedicalRecord createMedicalRecord(MedicalRecordDTO medicalRecord) {
		Optional<Checkin> optionalCheckin = checkinService.findById(medicalRecord.getCheckinId());
		Checkin appoint = optionalCheckin.orElse(null);
		MedicalRecord record = new MedicalRecord(medicalRecord.getDoctorId(), medicalRecord.getClinicProcess(),
				medicalRecord.getSumaryResult(), medicalRecord.getTreatment(), appoint);
//		record.setCheckin(appoint);
		appoint.setCommandFlag(2);
		record.setCommandFlag(0);
		record.setReleaseTime(timeNow());
		return repository.save(record);
	}

	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return repository.save(medicalRecord);
	}

	public void deleteMedicalRecord(int id) {
		repository.deleteById(id);
	}

	public int countRecordsInCurrentMonth() {
		return repository.countByReleaseTimeInCurrentMonth();
	}

	public long countOccurrencesByDoctorId(String doctorId) {
		return repository.countByDoctorId(doctorId);
	}
}
