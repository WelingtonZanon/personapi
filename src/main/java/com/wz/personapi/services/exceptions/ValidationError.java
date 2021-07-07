package com.wz.personapi.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.wz.personapi.services.validation.FieldMessage;

public class ValidationError extends StandardError {

	private List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErros() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

}
