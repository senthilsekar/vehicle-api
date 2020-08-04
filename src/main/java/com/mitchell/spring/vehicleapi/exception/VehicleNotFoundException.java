package com.mitchell.spring.vehicleapi.exception;

public class VehicleNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4241266712289951971L;

	public VehicleNotFoundException(String message) {
		super(message);
	}

	
	
}
