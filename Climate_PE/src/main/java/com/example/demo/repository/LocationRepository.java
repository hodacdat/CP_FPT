package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Location;
import com.example.demo.entity.Role;
import com.example.demo.entity.Symptom;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	Role findByName(String name);

	@Query("select c from Location c where c.commandFlag = 0")
	List<Location> findLocation();

	@Query("select c from Location c ")
	List<Location> findLocationForAdmin();

	@Query("SELECT ia FROM Location ia WHERE ia.id = :id")
	Location getLoById(@Param(value = "id") int id);
	
	@Query("select i from Location i where  CONCAT(i.name, ' - ', i.description) = :location")
	Location findLocate(@Param(value = "location") String location);
}