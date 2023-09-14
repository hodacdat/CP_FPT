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

import com.example.demo.DTO.SymptomDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.SymptomRepository;
import com.example.demo.service.SymptomService;

@RestController
@RequestMapping("/symptom")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class SymptomController {

	@Autowired
	private SymptomService service;

	@Autowired
	private SymptomRepository repository;

	@PostMapping("/save")
	public String save(@RequestBody SymptomDTO symtom) {
		service.save(symtom);
		return "success";
	}

	@PutMapping("/update")
	public String update(@RequestBody SymptomDTO symtom) {
		service.update(symtom);
		return "success";
	}

	@GetMapping("/list")
	public List<Symptom> getAll() {
		return service.findAll();
	}

	@GetMapping("/listadmin")
	public List<Symptom> getAllForAdmin() {
		return service.findAllForAdmin();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Symptom> getSymptomById(@PathVariable(value = "id") Integer id) {
		Optional<Symptom> symtom = service.findById(id);
		if (symtom.isPresent()) {
			return ResponseEntity.ok().body(symtom.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/block")
	public String blockSymptom(@RequestParam(value = "id") String id) {
		System.out.println(id);
		Symptom acc = null;
		int idc = Integer.parseInt(id);
		acc = repository.getSyById(idc);
		if (acc != null) {
			String result = service.blockSymptom(acc);
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
