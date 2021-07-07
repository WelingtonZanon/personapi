package com.wz.personapi.DTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.wz.personapi.entities.Phone;
import com.wz.personapi.enums.PhoneType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

	private Long id;
	@Enumerated(EnumType.STRING) 
	private PhoneType type;
	@NotEmpty (message = "Campo obrigatório")
	@Size(min=13, max=14)
	private String number;
	
	public PhoneDTO(Phone entity) {
		this.id = entity.getId();
		this.type = entity.getType();
		this.number = entity.getNumber();
	}
}
