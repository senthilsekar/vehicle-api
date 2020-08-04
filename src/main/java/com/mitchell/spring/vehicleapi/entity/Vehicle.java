package com.mitchell.spring.vehicleapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 *   Vehicle Entity
 *   
 * @author senthil
 *
 */

@Entity
public class Vehicle {

	@Id
	@Column(name = "vehicleId")
	public Integer id;
	
	@Column(name="vehicleYear")
	@Min(1950)
	@Max(2050)
	public Integer vehicleYear;
	
	@Column(name = "make")
	@NotEmpty(message = "make is required")
	public String make;
	
	@Column(name = "model")
	@NotEmpty(message = "model is required")
	public String model;
	
	public Vehicle() {
		
	}
	
	public Vehicle(Integer id, Integer year, String make, String model) {
		super();
		this.id = id;
		this.vehicleYear = year;
		this.make = make;
		this.model = model;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getYear() {
		return vehicleYear;
	}
	public void setYear(Integer year) {
		this.vehicleYear = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((vehicleYear == null) ? 0 : vehicleYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (vehicleYear == null) {
			if (other.vehicleYear != null)
				return false;
		} else if (!vehicleYear.equals(other.vehicleYear))
			return false;
		return true;
	}
	
	
	
}
