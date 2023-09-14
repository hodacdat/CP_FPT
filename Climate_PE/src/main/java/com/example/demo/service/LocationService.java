package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Location;
import com.example.demo.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository repository;

	public Location save(Location location) {
		return repository.save(location);
	}

	public List<Location> findAll() {
		return repository.findLocation();
	}

	public List<Location> findAllForAdmin() {
		return repository.findLocationForAdmin();
	}

	public Optional<Location> findById(Integer id) {
		return repository.findById(id);
	}
	public Location findByIdAcc(String location) {
		return repository.findLocate(location);
	}
	public String blockLocation(Location c) {
		c.setCommandFlag(2);
		repository.save(c);
		return "success";
	}
}
