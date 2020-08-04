package com.mitchell.spring.vehicleapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchell.spring.vehicleapi.controllers.VehicleApiController;
import com.mitchell.spring.vehicleapi.entity.Vehicle;
import com.mitchell.spring.vehicleapi.repositories.VehicleRepository;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleApiControllerUnitTestWithMock {

	@Autowired
	private VehicleApiController vehicleController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private VehicleRepository vehicleRepository;
	
	@Test
	void contextLoads() {
		assertThat(vehicleController).isNotNull();
	}
	
	
	private static List<Vehicle> getVehicleList() {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles.add(new Vehicle(1001, 2002, "honda", "se"));
		vehicles.add(new Vehicle(1002, 2015, "toyota", "sl"));
		vehicles.add(new Vehicle(1003, 1995, "nissan", "sl"));
		return vehicles;
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void getAllVehiclesTest() throws Exception{
		List<Vehicle> sampleInput = getVehicleList();
		
		Mockito.when(vehicleRepository.findAll()).thenReturn(sampleInput);
		
		mockMvc.perform(get("/vehicles"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(sampleInput.size())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1001));
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).findAll();
		
	}
	
	@Test
	public void getVehicleWithVehicleIdTest() throws Exception{
		
		Vehicle vehicle = new Vehicle(1003, 1995, "nissan", "sl");
		
		Mockito.when(vehicleRepository.findById(1003)).thenReturn(Optional.ofNullable(vehicle));
		
		mockMvc.perform(get("/vehicles/{id}",1003))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))		
		.andExpect(MockMvcResultMatchers.jsonPath("$.make").value("nissan"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.year").value(1995));
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).findById(1003);
		
	}
	
	@Test
	public void getVehicleWithVehicleIdErrorTest() throws Exception{
		
		Vehicle vehicle = null;
		
		Mockito.when(vehicleRepository.findById(1003)).thenReturn(Optional.ofNullable(vehicle));
		
		mockMvc.perform(get("/vehicles/{id}",1003))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))		
		.andExpect(content().string("Vehicle with id 1003 not found"));
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).findById(1003);
		
	}
	
	@Test
	public void addVehicleTest() throws Exception 
	{
		Vehicle vehicle = new Vehicle(1003, 1995, "nissan", "sl");
		Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		Mockito.when(vehicleRepository.existsById(vehicle.getId())).thenReturn(false);
		
		mockMvc.perform( MockMvcRequestBuilders
	      .post("/vehicles")
	      .content(asJsonString(vehicle))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicle.getId());
	}
	
	@Test
	public void addVehicleErrorTest() throws Exception 
	{
		Vehicle vehicle = new Vehicle(1003, 1995, "nissan", "sl");
		Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		Mockito.when(vehicleRepository.existsById(vehicle.getId())).thenReturn(true);
		
		mockMvc.perform( MockMvcRequestBuilders
	      .post("/vehicles")
	      .content(asJsonString(vehicle))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.TEXT_PLAIN))
	      .andExpect(status().isBadRequest())
	      .andExpect(content().string("Vehicle with id 1003 Already Exist"));
		
		Mockito.verify(vehicleRepository, Mockito.times(0)).save(vehicle);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicle.getId());
	}
	
	
	@Test
	public void updateVehicleTest() throws Exception 
	{
		Vehicle vehicle = new Vehicle(1003, 1995, "nissan", "sl");
		Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		Mockito.when(vehicleRepository.existsById(vehicle.getId())).thenReturn(true);
		
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/vehicles")
	      .content(asJsonString(vehicle))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNoContent())
	      .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicle.getId());
	}

	
	@Test
	public void updateVehicleErrorTest() throws Exception 
	{
		Vehicle vehicle = new Vehicle(1002, 1995, "nissan", "sl");
		Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
		Mockito.when(vehicleRepository.existsById(vehicle.getId())).thenReturn(false);
		
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/vehicles")
	      .content(asJsonString(vehicle))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.TEXT_PLAIN))
	      .andExpect(status().isNotFound())
	      .andExpect(content().string("Vehicle with id 1002 not found"));
		
		Mockito.verify(vehicleRepository, Mockito.times(0)).save(vehicle);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicle.getId());
	}
	
	@Test
	public void deleteVehicleTest() throws Exception 
	{
		Integer vehicleIdtoDelete = 1005;
		Mockito.doNothing().when(vehicleRepository).deleteById(vehicleIdtoDelete); 
		Mockito.when(vehicleRepository.existsById(vehicleIdtoDelete)).thenReturn(true);
		
		mockMvc.perform( MockMvcRequestBuilders.delete("/vehicles/{id}", vehicleIdtoDelete) )
		 .andExpect(status().isNoContent())
		 .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
		
		Mockito.verify(vehicleRepository, Mockito.times(1)).deleteById(vehicleIdtoDelete);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicleIdtoDelete);
	}
	
	@Test
	public void deleteVehicleErrorTest() throws Exception 
	{
		Integer vehicleIdtoDelete = 1005;
		Mockito.doNothing().when(vehicleRepository).deleteById(vehicleIdtoDelete); 
		Mockito.when(vehicleRepository.existsById(vehicleIdtoDelete)).thenReturn(false);
		
		mockMvc.perform( MockMvcRequestBuilders
		  .delete("/vehicles/{id}", vehicleIdtoDelete) 
		  .accept(MediaType.TEXT_PLAIN))
	      .andExpect(status().isNotFound())
	      .andExpect(content().string("Vehicle with id 1005 not found"));
		
		Mockito.verify(vehicleRepository, Mockito.times(0)).deleteById(vehicleIdtoDelete);
		Mockito.verify(vehicleRepository, Mockito.times(1)).existsById(vehicleIdtoDelete);
	}
}
