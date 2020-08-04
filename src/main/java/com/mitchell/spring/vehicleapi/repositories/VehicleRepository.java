package com.mitchell.spring.vehicleapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mitchell.spring.vehicleapi.entity.Vehicle;

/**
 *   This Vehicle Interface contains all the find querys that implicitly implemented by the Spring data
 * 
 *   @author senthil
 *
 */
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

	
	public List<Vehicle> findByMake(String make);
	public List<Vehicle> findByModel(String model);
	public List<Vehicle> findByVehicleYear(Integer vehicleYear);
	
	public List<Vehicle> findByModelAndVehicleYear(String model, Integer vehicleYear);
	public List<Vehicle> findByMakeAndVehicleYear(String make, Integer vehicleYear);
	public List<Vehicle> findByMakeAndModel(String make, String model);
	
	public List<Vehicle> findByMakeAndModelAndVehicleYear(String make, String model, Integer vehicleYear);
}
