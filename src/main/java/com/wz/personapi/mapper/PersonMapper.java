package com.wz.personapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.entities.Person;

@Mapper
public interface PersonMapper {

	PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
	
	Person toModel(PersonDTO personDTO);
	PersonDTO toDTO(Person person);
	
}
