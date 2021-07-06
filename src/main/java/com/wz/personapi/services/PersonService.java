package com.wz.personapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.entities.Person;
import com.wz.personapi.mapper.PersonMapper;
import com.wz.personapi.repositories.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
	private PersonRepository personRepository;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	public PersonDTO insert(PersonDTO dto) {
		Person personToSave = personMapper.toModel(dto);
		Person savePerson = personRepository.save(personToSave);
		return new PersonDTO(savePerson);
	}
	
}
