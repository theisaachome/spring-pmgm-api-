package com.highway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistException(String message) {
		super(message);
	}
	
	
	

}
