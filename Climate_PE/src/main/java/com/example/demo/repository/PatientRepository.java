package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Location;
import com.example.demo.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	@Query("select p FROM Patient p where LOWER(p.email) = LOWER(:email)")
	Patient findByEmail(@Param("email") String email);

	@Query("select p FROM Patient p where LOWER(p.name) = LOWER(:name)")
	Patient findByName(@Param("name") String name);

	@Query("select p FROM Patient p where p.id = :id")
	Patient findByID(@Param("id") String id);

	@Query("select p FROM Patient p where p.id = :id and LOWER(p.name) = LOWER(:name)")
	Patient findByIDAndName(@Param("id") String id, @Param("name") String name);

	@Query("select c from Patient c where c.commandFlag in (0,1)")
	List<Patient> findPatients();

	@Query("select c from Patient c ")
	List<Patient> findPatientsForAdmin();

}
