package com.mitchell.spring.vehicleapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitchell.spring.vehicleapi.entity.Vehicle;
import com.mitchell.spring.vehicleapi.exception.VehicleAlreadyExistException;
import com.mitchell.spring.vehicleapi.exception.VehicleNotFoundException;
import com.mitchell.spring.vehicleapi.repositories.VehicleRepository;

/**
 *  Service class to implement the Add/view/update/delete features for Vehicle Entity
 *  
 *  @author senthil
 *
 */
@Service
public class VehicleApiService {

	@Autowired
	private VehicleRepository vehicleRepository;

	/**
	 *   This Method fetches all the vehicle from the repository
	 *   
	 *   @return list of vehicle object
	 */
	public List<Vehicle> getAllVehicles(){
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicleRepository.findAll().forEach(vehicle -> vehicles.add(vehicle));		
		return vehicles;
	}
	
	/** 
	 *  This method fetches vehicles list based on matching filter attributes that are given
	 *  
	 * @param make
	 * @param model
	 * @param vehicleYear
	 * @return
	 */
	public List<Vehicle> getVehiclesFiltered(String make, String model, Integer vehicleYear){
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		if(make!=null && model != null && vehicleYear!=null) {
			vehicles = vehicleRepository.findByMakeAndModelAndVehicleYear(make,model,vehicleYear);
		}else if(make!=null && model !=null && vehicleYear==null) {
			vehicles = vehicleRepository.findByMakeAndModel(make, model);
		}else if(make==null && model !=null && vehicleYear!=null) {
			vehicles = vehicleRepository.findByModelAndVehicleYear(model, vehicleYear);
		}else if(make!=null && model ==null && vehicleYear!=null) {
			vehicles = vehicleRepository.findByMakeAndVehicleYear(make, vehicleYear);
		}else if(make!=null) {
			vehicles = vehicleRepository.findByMake(make);
		}else if(model!=null) {
			vehicles = vehicleRepository.findByModel(model);
		}else if(vehicleYear!=null) {
			vehicles = vehicleRepository.findByVehicleYear(vehicleYear);
		}
		
		return vehicles;
	}
	
	/**
	 *  This method Fetches single Vehicle Entity for the given vehicleId,
	 *  it throws VehicleNotFound Runtime Exception if there is no matching vehicleID
	 * 
	 *  @param id
	 *  @return
	 */
	public Vehicle getVehicle(Integer id) {
		//Optional<Vehicle> vehicle= vehicles.stream().filter(t -> t.getId().equals(id)).findFirst();
		Optional<Vehicle> vehicle= vehicleRepository.findById(id);
		if(vehicle.isPresent()){
			return vehicle.get();
		}else{
			throw new VehicleNotFoundException("Vehicle with id " + id + " not found");
		}
		
	}
	
	/**
	 *  This method add the given Vehicle Entity to the repository,
	 *  it throws VehicleAlreadyExist Runtime Exception if the Vehicle data already present in repository
	 *  
	 *  @param vehicle
	 */
	public void addVehicle(Vehicle vehicle) {
		if(vehicleRepository.existsById(vehicle.getId())) {
			throw new VehicleAlreadyExistException("Vehicle with id " + vehicle.getId() + " Already Exist");
		}else {
			vehicleRepository.save(vehicle);
		}
	}	
	
	/**
	 * This method update the Vehicle Entity in the repository,
	 *  it throws VehicleNotFound Runtime Exception if there is no matching Vehicle to update
	 * @param vehicle
	 */
	public void updateVehicle(Vehicle vehicle) {
		if(vehicleRepository.existsById(vehicle.getId())) {
			vehicleRepository.save(vehicle);
		}else{
			throw new VehicleNotFoundException("Vehicle with id " + vehicle.getId() + " not found");
		}
	}
	
	
	/**
	 *  This method delete the Vehicle Entity in the repository 
	 *   it throws VehicleNotFound Runtime Exception if there is no matching vehicle to Delete
	 *  @param id
	 */
	public void deleteVehicle(Integer id) {
		if(vehicleRepository.existsById(id)) {
			vehicleRepository.deleteById(id);
		}else{
			throw new VehicleNotFoundException("Vehicle with id " + id + " not found");
		}
	}
	
}
