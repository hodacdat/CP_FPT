package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("select p FROM Appointment p where p.patient.id = :patient_id and p.doctorName = :doctorName and "
			+ "p.examDate = :examDate and p.examTime = :examTime")
	Appointment findOne(@Param("patient_id") String patient_id, @Param("doctorName") String doctorName,
			@Param("examDate") String examDate, @Param("examTime") String examTime);

	@Query("select p from Appointment p where p.commandFlag = '0'")
	List<Appointment> getAllIncome();

	@Query("SELECT a FROM Appointment a WHERE a.patient.id = :patient_id")
	List<Appointment> findByPaintedId(@Param("patient_id") String paintedId);

	@Query("select p FROM Appointment p where p.id = :id")
	Appointment findByID(@Param("id") int id);
}
