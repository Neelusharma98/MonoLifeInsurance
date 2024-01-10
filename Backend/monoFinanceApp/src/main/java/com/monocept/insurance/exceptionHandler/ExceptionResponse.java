package com.monocept.insurance.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.monocept.insurance.exception.*;
import com.monocept.insurance.exception.Error;

@ControllerAdvice
	public class ExceptionResponse {
		

		 @ExceptionHandler(InsuranceException.class)
		    public ResponseEntity<Error> handleNameNotValidException(InsuranceException ex) {
		        Exception exception = new Exception();
		        Error error = new Error(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),System.currentTimeMillis());
		        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		    }
		 

}
