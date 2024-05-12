package com.rest.web.inka.models;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;
	public Date getTimestamp() {
		return timestamp;
	}
	
	
	
	public ApiError() {
		// TODO Auto-generated constructor stub
	}



	public ApiError(Date timestamp, HttpStatus status, String message, List<String> errors) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	} 
    
    
}
