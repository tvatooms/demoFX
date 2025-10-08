package com.tva.RestEngine;

class PartitionNotFoundException extends RuntimeException {

	PartitionNotFoundException(Long id) {
		super("Could not find partition " + id);
	}
}
