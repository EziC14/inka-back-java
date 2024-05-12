package com.rest.web.inka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.ErrorResponseException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rest.web.inka.models.ApiError;

import jakarta.annotation.Nullable;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders header, HttpStatus status, WebRequest request){

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField().concat(" ").concat(err.getDefaultMessage()))
				.collect(Collectors.toList());
	
		ApiError err = new ApiError(
	            new Date(),
	            HttpStatus.BAD_REQUEST, 
	            "ERROR" ,
	            errors);
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex,
			 HttpHeaders headers,
			 HttpStatusCode status,
			 WebRequest request){
		
		
		
		return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
	}
	@Nullable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
	 HttpHeaders headers,
	 HttpStatusCode status,
	 WebRequest request){
		List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        
        ApiError err = new ApiError(
            new Date(),
            HttpStatus.BAD_REQUEST, 
            "Malformed JSON request" ,
            details);
        
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}

}
