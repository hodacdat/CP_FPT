package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {

	@Query("select c from Symptom c where c.commandFlag = 0")
	List<Symptom> findSymtom();

	@Query("select c from Symptom c ")
	List<Symptom> findSymtomForAdmin();

	@Query("SELECT ia FROM Symptom ia WHERE ia.id = :id")
	Symptom getSyById(@Param(value = "id") int id);
}
