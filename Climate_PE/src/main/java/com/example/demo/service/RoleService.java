package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Location;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	public Role save(Role role) {
		return repository.save(role);
	}

	public Role update(Role role) {
		Role c = repository.findById(role.getId())
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id " + role.getId()));

		return repository.save(role);
	}

	public List<Role> findAll() {
		return repository.findAll();
	}
	public List<Role> findAllForAdmin() {
		return repository.findAllForAdmin();
	}

	public Optional<Role> findById(Integer id) {
		return repository.findById(id);
	}
	
	public Role findByIdac(int id) {
		return repository.getRoById(id);
	}
	public String blockRole(Role c) {
		c.setCommandFlag(2);
		repository.save(c);
		return "success";
	}
}
