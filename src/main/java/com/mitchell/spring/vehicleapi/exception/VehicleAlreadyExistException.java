package com.mitchell.spring.vehicleapi.exception;

public class VehicleAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -4241266712289951971L;

	public VehicleAlreadyExistException(String message) {
		super(message);
	}

	
	
}
