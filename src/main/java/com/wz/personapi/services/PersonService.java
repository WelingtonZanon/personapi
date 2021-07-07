package com.wz.personapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.entities.Person;
import com.wz.personapi.mapper.PersonMapper;
import com.wz.personapi.repositories.PersonRepository;
import com.wz.personapi.services.exceptions.ResourseNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
	private PersonRepository personRepository;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	@Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(Pageable pageable){
		Page<Person> page = personRepository.findAll(pageable);
		return page.map(x -> new PersonDTO(x));
	}
	
	@Transactional(readOnly = true)
	public PersonDTO findByID(Long id) throws ResourseNotFoundException {
		Person person = verifyIfExists(id);
		return new PersonDTO(person);
	}
	
	@Transactional
	public PersonDTO insert(PersonDTO dto) {
		Person personToSave = personMapper.toModel(dto);
		Person savePerson = personRepository.save(personToSave);		
		return new PersonDTO(savePerson);
	}
	
	@Transactional
	public void delete (Long id) throws ResourseNotFoundException{
		verifyIfExists(id);
		personRepository.deleteById(id);
	}
	
	@Transactional
	public PersonDTO updateById(Long id, PersonDTO personDTO) throws ResourseNotFoundException {
		verifyIfExists(id);
		Person personToUpdate = personMapper.toModel(personDTO);
		Person updatedPerson = personRepository.save(personToUpdate);
		return new PersonDTO(updatedPerson);
	}
	
	private Person verifyIfExists (Long id) throws ResourseNotFoundException {
		return personRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Entity not found"));
	}
}
