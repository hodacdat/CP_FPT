package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.SymptomDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Role;
import com.example.demo.entity.Specialty;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.SpeciatlyRepository;
import com.example.demo.repository.SymptomRepository;

@Service
public class SymptomService {

	@Autowired
	private SymptomRepository repository;
	@Autowired
	private SpeciatlyRepository speciatlyRepository;

	public Symptom save(SymptomDTO symtom) {
		int specid = Integer.parseInt(symtom.getSpecid());
		Specialty sp = speciatlyRepository.getSpById(specid);

		if (sp != null) {
			Symptom s = new Symptom(symtom.getName(), symtom.getDescription(), sp);
			return repository.save(s);
		}
		return null;
	}

	public Symptom update(SymptomDTO symptom) {
		int idS = Integer.parseInt(symptom.getId());
		int idSp = Integer.parseInt(symptom.getSpecid());
		int cmf = Integer.parseInt(symptom.getCommandFlag());

		Symptom c = repository.findById(idS)
				.orElseThrow(() -> new EntityNotFoundException("Symptom not found with id " + symptom.getId()));

		Specialty sp = speciatlyRepository.getSpById(idSp);

		c.setName(symptom.getName());
		c.setDescription(symptom.getDescription());
		c.setSpecialty(sp);
		c.setCommandFlag(cmf);
		return repository.save(c);
	}

	public List<Symptom> findAll() {
		return repository.findSymtom();
	}

	public List<Symptom> findAllForAdmin() {
		return repository.findSymtomForAdmin();
	}

	public Optional<Symptom> findById(Integer id) {
		return repository.findById(id);
	}

	public String blockSymptom(Symptom c) {
		c.setCommandFlag(2);
		repository.save(c);
		return "success";
	}
}
