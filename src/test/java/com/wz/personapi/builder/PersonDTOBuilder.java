package com.wz.personapi.builder;

import java.time.Instant;

import com.wz.personapi.DTO.PersonDTO;

import lombok.Builder;

@Builder
public class PersonDTOBuilder {
	
	@Builder.Default
	private Long id = 1L;
	
	@Builder.Default
	private String firstName = "Maria";
	
	@Builder.Default
	private String lastName = "Silva"; 
	
	@Builder.Default
	private String cpf = "050.217.379-37";
	
	@Builder.Default
	private Instant birthDate = Instant.now();	

	public PersonDTO toPersonDTO() {
		return new PersonDTO(
				id,
				firstName,
				lastName,
				cpf,
				birthDate
				);				
	}
}
