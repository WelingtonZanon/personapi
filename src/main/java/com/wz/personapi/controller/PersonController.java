package com.wz.personapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.services.PersonService;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/person")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

	private PersonService service;
	
	@PostMapping
	public PersonDTO insertPerson(@RequestBody PersonDTO dto) {
		return service.insert(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<PersonDTO>> findAll(Pageable pageable){
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("firstName"));
		Page<PersonDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
		PersonDTO dto = service.findByID(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) throws NotFoundException {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	public PersonDTO updateById(@PathVariable Long id, @RequestBody PersonDTO dto) {
		return service.updateById(id, dto);
	}
}
