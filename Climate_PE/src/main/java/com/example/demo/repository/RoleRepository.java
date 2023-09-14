package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Location;
import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);

	@Query("select c from Role c ")
	List<Role> findAllForAdmin();

	@Query("select c from Role c where c.commandFlag = 0")
	List<Role> findAll();

	@Query("SELECT ia FROM Role ia WHERE ia.id = :id")
	Role getRoById(@Param(value = "id") int id);

}