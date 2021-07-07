package com.wz.personapi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.entities.Person;
import com.wz.personapi.repositories.PersonRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonInsertValidator implements ConstraintValidator<PersonInsertValid, PersonDTO>{
	
	private PersonRepository personRepository;
	
	@Override
	public void initialize(PersonInsertValid ann) {
	}

	@Override
	public boolean isValid(PersonDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Person person = personRepository.findByCpf(dto.getCpf());
		
		if(person!=null) {
			list.add(new FieldMessage("cpf"/*campo DTO a ser checado**/, "CPF já esta cadastrado."));
		}
		
		for(FieldMessage msg : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(msg.getMessage()).addPropertyNode(msg.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
	

}
