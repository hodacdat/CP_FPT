package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
	MedicalRecord findByCheckinId(int checkinId);

	@Query("select i from MedicalRecord i where i.doctorId = :id")
	List<MedicalRecord> findAllByDoctorId(@Param(value = "id") String id);
	
	@Query("select i from MedicalRecord i where i.checkin.patient.id = :id")
	List<MedicalRecord> findAllByPatientId(@Param(value = "id") String id);
	
	@Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE MONTH(mr.releaseTime) = MONTH(CURRENT_DATE) AND YEAR(mr.releaseTime) = YEAR(CURRENT_DATE)")
    int countByReleaseTimeInCurrentMonth();
	
	@Query("SELECT COUNT(m) FROM MedicalRecord m WHERE m.doctorId = :doctorId")
    long countByDoctorId(@Param(value = "doctorId")String doctorId);
}
