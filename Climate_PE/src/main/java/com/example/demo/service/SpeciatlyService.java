package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Location;
import com.example.demo.entity.Specialty;
import com.example.demo.repository.SpeciatlyRepository;

@Service
public class SpeciatlyService {

	@Autowired
	private SpeciatlyRepository repository;

	public Specialty save(Specialty specialty) {
		return repository.save(specialty);
	}

	public Specialty update(Specialty specialty) {
		Specialty c = repository.findById(specialty.getId())
				.orElseThrow(() -> new EntityNotFoundException("Specialty not found with id " + specialty.getId()));

		return repository.save(specialty);
	}

	public List<Specialty> findAll() {
		return repository.findSpecialty();
	}

	public List<Specialty> findAllForAdmin() {
		return repository.findSpecialtyForAdmin();
	}

	public Optional<Specialty> findById(Integer id) {
		return repository.findById(id);
	}
	public Specialty findByIdAcc(int id) {
		return repository.getSpById(id);
	}
	
	public String findByName(String name) {
		return repository.getSepByName(name);
	}
	
	public String blockSpec(Specialty c) {
		c.setCommandFlag(2);
		repository.save(c);
		return "success";
	}
}
