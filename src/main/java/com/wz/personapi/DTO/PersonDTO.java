package com.wz.personapi.DTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.wz.personapi.entities.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

	private Long id;
	@NotEmpty
	@Size(min=2, max=100)
	private String firstName;
	@NotEmpty
	@Size(min=2, max=100)
	private String lastName;
	@NotEmpty
	@CPF
	private String cpf;
	private Instant birthDate;
	@Valid
	@NotEmpty
	private final List<PhoneDTO> phones = new ArrayList<>();
	
	public PersonDTO(Person entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		cpf = entity.getCpf();
		birthDate = entity.getBirthDate();
		entity.getPhones().forEach(phone -> this.phones.add(new PhoneDTO(phone)));
	}
}
