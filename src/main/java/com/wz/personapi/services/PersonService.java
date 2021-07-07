package com.wz.personapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.entities.Person;
import com.wz.personapi.mapper.PersonMapper;
import com.wz.personapi.repositories.PersonRepository;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
	private PersonRepository personRepository;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	@Transactional
	public PersonDTO insert(PersonDTO dto) {
		Person personToSave = personMapper.toModel(dto);
		Person savePerson = personRepository.save(personToSave);
		return new PersonDTO(savePerson);
	}
	
	@Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(Pageable pageable){
		Page<Person> page = personRepository.findAll(pageable);
		return page.map(x -> new PersonDTO(x));
	}
	
	@Transactional(readOnly = true)
	public PersonDTO findByID(Long id) {
		Optional<Person> obj = personRepository.findById(id);
		Person entity = obj.orElseThrow(() -> new ConfigDataResourceNotFoundException(null));
		return new PersonDTO(entity);
	}
	
	@Transactional
	public void delete (Long id) throws NotFoundException{
		personRepository.deleteById(id);
	}
	
	@Transactional
	public PersonDTO updateById(Long id, PersonDTO personDTO) {
		Person personToUpdate = personMapper.toModel(personDTO);
		Person updatedPerson = personRepository.save(personToUpdate);
		return new PersonDTO(updatedPerson);
	}
}
