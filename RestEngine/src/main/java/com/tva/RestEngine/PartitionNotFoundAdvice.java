package com.tva.RestEngine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class PartitionNotFoundAdvice {

	@ExceptionHandler(PartitionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String PartitionNotFoundHandler(PartitionNotFoundException ex) {
		return ex.getMessage();
	}
}
