package com.wz.personapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.services.PersonService;

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
	
}
