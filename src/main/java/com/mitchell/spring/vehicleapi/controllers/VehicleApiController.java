package com.mitchell.spring.vehicleapi.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mitchell.spring.vehicleapi.entity.Vehicle;
import com.mitchell.spring.vehicleapi.services.VehicleApiService;

/**
 *  This class handles all the REST request for Vehicl API
 *  
 * @author senthil
 *
 */
@RestController
@Validated
@CrossOrigin
public class VehicleApiController {

	@Autowired
	private VehicleApiService vehicleService;

	/**
	 *    This method handles the GET request for the Vehciles matching the provided filter, 
	 *    if no filter specified returns all vehicles
	 *     
	 * @param make (optional request param)
	 * @param model (optional request param)
	 * @param year (optional request param)
	 * 
	 *    @return list of all Vehicles matching given filter(optional),  default: all vehicle data is returned 
	 */
	@RequestMapping("/vehicles")
	public List<Vehicle> getAllVehicles(@RequestParam(value = "make", required = false) String make,
										@RequestParam(value = "model", required = false) String model,
										@RequestParam(value = "year", required = false) Integer year){
		if(year != null || make != null || model != null) {
			return vehicleService.getVehiclesFiltered(make, model, year);
		}else {
			return vehicleService.getAllVehicles();
		}
		
	}
	
	/** This method handles the GET request for a given vehicleId
	 * 
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping("/vehicles/{id}")
	public Vehicle getVehicle(@PathVariable("id") @Min(1) Integer vehicleId){
		return vehicleService.getVehicle(vehicleId);
		
	}
	
	/** This method handles the POST request (Add) for adding Vehicle
	 * 
	 * @param vehicle Object to add
	 * @return
	 */
	@RequestMapping(path = "/vehicles" , method = RequestMethod.POST)
	public ResponseEntity<Object> addVehicle(@RequestBody @Valid Vehicle vehicle) {
		vehicleService.addVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.CREATED).build();  //201 - Created
	}
	
	/** This method handles PUT request (update) for the Vehicle
	 * 
	 * @param vehicle Object to update
	 * @return
	 */
	@RequestMapping(path = "/vehicles" , method = RequestMethod.PUT)
	public ResponseEntity<Object> updateVehicle(@RequestBody @Valid Vehicle vehicle){
		vehicleService.updateVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204 - success with no body content
		
	}
	
	/** This method handles the DELETE request for Vehicle 
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping(path = "/vehicles/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteVehicle(@PathVariable("id") @Valid @Min(1) Integer vehicleId){
		 vehicleService.deleteVehicle(vehicleId);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204 - success with no body content
		
	}
	
}
