package com.wz.personapi.services.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceEsceptionHandler {

	HttpStatus status;
	
	@ExceptionHandler (ResourseNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourseNotFoundException e, HttpServletRequest request){
		status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		//return personal error on body
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
	/*
	 * fieldError, para pegar o erro do "valid" do spring que foi passado nos DTO.
	 * o BInding é da biblioteca bins validadion.
	 */
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}
		
		//comando propio do java para retornar a requizição de erro no body da pagina.
		return ResponseEntity.status(status).body(err);
	}
}
