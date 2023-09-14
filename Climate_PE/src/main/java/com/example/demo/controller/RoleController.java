package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Location;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class RoleController {

	@Autowired
	private RoleService service;

	@Autowired
	private RoleRepository repository;

	@PostMapping("/save")
	public String save(@RequestBody Role role) {
		service.save(role);
		return "success";
	}

	@PutMapping("/update")
	public String update(@RequestBody Role role) {
		System.out.println(role.toString());
		service.update(role);
		return "success";
	}

	@GetMapping("/list")
	public List<Role> getAll() {
		return service.findAll();
	}

	@GetMapping("/listadmin")
	public List<Role> getAllForAdmin() {
		return service.findAllForAdmin();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Integer id) {
		Optional<Role> role = service.findById(id);
		if (role.isPresent()) {
			return ResponseEntity.ok().body(role.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/block")
	public String blockRole(@RequestParam(value = "id") String id) {
		System.out.println(id);
		Role acc = null;
		int idc = Integer.parseInt(id);
		acc = repository.getRoById(idc);
		if (acc != null) {
			String result = service.blockRole(acc);
			if (result.equals("success")) {

				return "Block success";
			} else {
				return "Block fail";
			}
		} else {
			return "Not found role in system";
		}
	}
}
