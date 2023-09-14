package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Checkin;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

	@Query("select p from Checkin p where p.commandFlag = '0'")
	List<Checkin> getAllIncome();

	@Query("select p from Checkin p where p.commandFlag = '0' and p.doctorId = :doctorId")
	List<Checkin> getAllIncomeWithDoctorid(@Param("doctorId") String doctorId);

	@Query("SELECT a FROM Checkin a WHERE a.patient.id = :patient_id")
	List<Checkin> findByPaintedId(@Param("patient_id") String paintedId);

	@Query("SELECT a FROM Checkin a WHERE a.doctorId = :doctorId")
	List<Checkin> findByDoctorId(@Param("doctorId") String doctorId);

	@Query("SELECT COUNT(c) FROM Checkin c WHERE c.commandFlag = 2 "
			+ "AND EXTRACT(MONTH FROM c.registerTime) = EXTRACT(MONTH FROM CURRENT_DATE)")
	int countCommandFlag2ForCurrentMonth();

	@Query("SELECT COUNT(c) FROM Checkin c WHERE c.commandFlag = 3 "
			+ "AND EXTRACT(MONTH FROM c.registerTime) = EXTRACT(MONTH FROM CURRENT_DATE)")
	int countCommandFlag3ForCurrentMonth();
	
    @Query("SELECT c.doctorId, COUNT(c.id) FROM Checkin c GROUP BY c.doctorId")
    List<Object[]> countCheckinsByDoctor();
    

    @Query("SELECT c.doctorId, COUNT(c.id), c.speciatly FROM Checkin c GROUP BY c.doctorId")
    List<Object[]> countCheckinsByDoctorAndSpecial();
    
    @Query("SELECT COUNT(c.appointmentId) FROM Checkin c where c.doctorId = :doctorId")
	Long countAppointmentByDoctor(@Param("doctorId") String doctorId);
    
    @Query("SELECT c FROM Checkin c where c.appointmentId = :appid")
    Checkin findByAppId(@Param("appid") String appid);

}
