package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.InternalAccount;

public interface InternalRepository extends JpaRepository<InternalAccount, Integer> {

	Optional<InternalAccount> findByEmail(String email);

	@Query("select i from InternalAccount i where i.role.name = 'DOCTOR' and i.commandFlag = 0")
	List<InternalAccount> findAllDoctor();
	
	@Query("select i from InternalAccount i where i.role.name = 'DOCTOR' and i.commandFlag = 0")
	List<InternalAccount> findAllDoctorForAdmin();

	@Query("select i from InternalAccount i where i.role.name = 'DOCTOR' and i.commandFlag = 0 AND i.name = :name and CONCAT(i.workingPlace.name, ' - ', i.workingPlace.description) = :location")
	InternalAccount findDoctor(@Param(value = "name") String name, @Param(value = "location") String location);

	@Query("select i from InternalAccount i where i.role.name = 'DOCTOR' and i.commandFlag = 0 and i.workingPlace.id = :location")
	List<InternalAccount> findAllDoctorByLocation(@Param(value = "location") int id);
	
	@Query("select i from InternalAccount i where i.role.name != 'ADMIN'")
	List<InternalAccount> findAll();

	@Query("SELECT ia FROM InternalAccount ia JOIN ia.specialty s WHERE ia.name LIKE %:name% AND ia.role.name = 'DOCTOR' and ia.commandFlag = 0 AND s.name LIKE %:specialty%")
	Optional<List<InternalAccount>> findByNameAndSpecialty(@Param("name") String name,
			@Param("specialty") String specialty);
	
	@Query("SELECT ia FROM InternalAccount ia JOIN ia.specialty s WHERE ia.name LIKE %:name% and ia.commandFlag = 0 AND ia.role.name = 'DOCTOR'")
	InternalAccount findDoctorByName(@Param("name") String name);
	
	@Query("SELECT ia FROM InternalAccount ia JOIN ia.specialty s WHERE ia.name LIKE %:name%")
	InternalAccount findInterByName(@Param("name") String name);

	@Query("SELECT ia FROM InternalAccount ia JOIN ia.specialty s WHERE ia.name LIKE %:name% and ia.commandFlag = 0 AND ia.role.name = 'DOCTOR'")
	Optional<List<InternalAccount>> findByName(@Param(value = "name") String name);

	@Query("SELECT ia FROM InternalAccount ia WHERE ia.specialty.name LIKE %:specialty% and ia.commandFlag = 0 AND ia.role.name = 'DOCTOR'")
	Optional<List<InternalAccount>> findBySpecialty(@Param(value = "specialty") String specialty);

	@Query("SELECT ia FROM InternalAccount ia WHERE ia.id = :id")
	InternalAccount getAccById(@Param(value = "id") int id);
	
	@Query("SELECT ia.name FROM InternalAccount ia WHERE ia.id = :id")
	String getAccByIdWithnameDoctor(@Param(value = "id") int id);
	
	@Query("SELECT COUNT(*) FROM InternalAccount ia WHERE LOWER(ia.email) = LOWER(:email)")
	int getAccByEmailWithnameDoctor(@Param(value = "email") String email);
}
