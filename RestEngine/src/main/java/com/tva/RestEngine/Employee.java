package com.tva.RestEngine;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Partition {

	private @Id @GeneratedValue Long id;
	private String name;
	 

	Partition() {}

	Partition(String name) {

		this.name = name;
		 
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

 

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

 
	 

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.role);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
	}
}
