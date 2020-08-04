package com.mitchell.spring.vehicleapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.mitchell.spring.vehicleapi.entity.Vehicle;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = VehicleApiApplication.class, 
                 webEnvironment = WebEnvironment.RANDOM_PORT)

class VehicleApiControllerIntegrationTest {

	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
	
    @Test
    public void testAddViewUpdateDeleteVehicle() {
    	Vehicle vehicleToAdd = new Vehicle(1003, 1995, "nissan", "sl");
    	
    	{//---------------POST vehicles
		    	ResponseEntity<String> responseEntity = this.restTemplate
		            .postForEntity("http://localhost:" + port + "/vehicles", vehicleToAdd, String.class);
		        assertEquals(201, responseEntity.getStatusCodeValue());
    	}
        {//---------------GET vehicles
		       Vehicle[] vehicleArray=  this.restTemplate
		                .getForObject("http://localhost:" + port + "/vehicles", Vehicle[].class);
		        
		       assertTrue(vehicleArray.length == 1);
		       assertTrue(vehicleArray[0].equals(vehicleToAdd));   //vehicle obj that is added via POST is compared after GET ALL
        }
        
        {//--------------GET vehicles/{id}
	        Vehicle singleVehicle=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/vehicles/"+vehicleToAdd.getId(), Vehicle.class);     
	        assertTrue(singleVehicle.equals(vehicleToAdd));   //vehicle obj that is added via POST is compared after GET
        }
        
        {//---------------PUT vehicles
        	vehicleToAdd.setMake("nissan-updated");
	    	this.restTemplate.put("http://localhost:" + port + "/vehicles", vehicleToAdd);	        
	   }
        
        {//--------------GET vehicles/{id}  --- to read the PUT value
	        Vehicle singleVehicle=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/vehicles/"+vehicleToAdd.getId(), Vehicle.class);     
	        assertTrue(singleVehicle.getMake().equals("nissan-updated"));   //vehicle obj that is added via POST is compared after GET
        }
        
        {//--------------DELETE vehicles/{id}
        	this.restTemplate.delete("http://localhost:" + port + "/vehicles/"+vehicleToAdd.getId());        	
        }
        
        {//--------------GET vehicles/{id}  --- to read the DELETE value
	        String errorMessage=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/vehicles/"+vehicleToAdd.getId(), String.class);     
	       assertTrue(errorMessage.equalsIgnoreCase("Vehicle with id 1003 not found"));
        }
        
    }
	
}
